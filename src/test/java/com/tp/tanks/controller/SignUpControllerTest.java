package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.model.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class SignUpControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<String> getCookie(ResponseEntity<User> user) {
        final List<String> cookie = user.getHeaders().get("Set-Cookie");

        assertNotNull(cookie);
        assertFalse(cookie.isEmpty());

        return cookie;
    }

    private List<String> signUp(User user, HttpStatus httpStatus) {
        final ResponseEntity<User> resultUser = restTemplate.postForEntity("/signUp", user, User.class);
        assertEquals(httpStatus, resultUser.getStatusCode());

        if(resultUser.getStatusCode() == HttpStatus.CREATED) {
            assertEquals(user.getUsername(), resultUser.getBody().getUsername());
        }

        return getCookie(resultUser);
    }

    private List<String> signIn(User user, HttpStatus httpStatus) {
        final ResponseEntity<User> result = restTemplate.postForEntity("/signIn", user, User.class);
        assertEquals(httpStatus, result.getStatusCode());

        if(result.getStatusCode() == HttpStatus.OK) {
            assertEquals(user.getEmail(), result.getBody().getEmail());
        }

        return getCookie(result);
    }

    private void logout(List<String> cookie, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity result = restTemplate.exchange("/logout", HttpMethod.GET, requestEntity, User.class);
        assertEquals(httpStatus, result.getStatusCode());
    }

    private void getProfile(List<String> cookie, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity<User> result = restTemplate.exchange("/profile", HttpMethod.GET, requestEntity, User.class);
        assertEquals(httpStatus, result.getStatusCode());
    }

    @Test
    public void testSignUp() {
        User testUser = UserGenerator.generateUser();

        final List<String> cookie = signUp(testUser, HttpStatus.CREATED);
        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testSignUpConflict() {
        User testUser = UserGenerator.generateUser();

        signUp(testUser, HttpStatus.CREATED);
        signUp(testUser, HttpStatus.FORBIDDEN);
    }

    @Test
    public void testSignIn() {
        User firstUser = UserGenerator.generateUser();
        signIn(firstUser, HttpStatus.FORBIDDEN);

        List<String> cookie = signUp(firstUser, HttpStatus.CREATED);
        logout(cookie, HttpStatus.OK);

        cookie = signIn(firstUser, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);
    }

    @Test
    public void testLogout() {
        User testUser = UserGenerator.generateUser();
        final List<String> cookie = signUp(testUser, HttpStatus.CREATED);

        logout(cookie, HttpStatus.OK);
        logout(cookie, HttpStatus.FORBIDDEN);//already logged out
    }

    @Test
    public void testGetProfile() {
        List<String> cookie = new ArrayList<>();
        getProfile(cookie, HttpStatus.FORBIDDEN);

        User testUser = UserGenerator.generateUser();
        cookie = signUp(testUser, HttpStatus.CREATED);
        getProfile(cookie, HttpStatus.OK);
        
        logout(cookie, HttpStatus.OK);
        getProfile(cookie, HttpStatus.FORBIDDEN);
    }
}