package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.model.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SignUpControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<String> getCookie(ResponseEntity<User> user) {
        List<String> cookie = user.getHeaders().get("Set-Cookie");

        if(cookie == null) {
            cookie =  new ArrayList<>();
        }
        return cookie;
    }

    private ResponseEntity<User> signUp(User user) {
        return restTemplate.postForEntity("/signUp", user, User.class);
    }

    private ResponseEntity<User> signIn(User user) {
        return restTemplate.postForEntity("/signIn", user, User.class);
    }

    private ResponseEntity<User> logout(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        return restTemplate.exchange("/logout", HttpMethod.GET, requestEntity, User.class);
    }

    private ResponseEntity<User> getProfile(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        return restTemplate.exchange("/profile", HttpMethod.GET, requestEntity, User.class);
    }

    @Test
    public void testSignUp() {
        User testUser = UserGenerator.generateUser();

        ResponseEntity<User> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());
    }

    @Test
    public void testSignUpConflict() {
        User testUser = UserGenerator.generateUser();

        ResponseEntity<User> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = signUp(testUser);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testSignIn() {
        User firstUser = UserGenerator.generateUser();

        ResponseEntity<User> resultUser = signIn(firstUser);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());

        resultUser = signUp(firstUser);
        logout(getCookie(resultUser));

        resultUser = signIn(firstUser);
        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());
    }

    @Test
    public void testLogout() {
        User testUser = UserGenerator.generateUser();

        ResponseEntity<User> resultUser = signUp(testUser);

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testGetProfile() {
        List<String> cookie = new ArrayList<>();
        ResponseEntity<User> resultUser = getProfile(cookie);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());

        User testUser = UserGenerator.generateUser();

        resultUser = signUp(testUser);
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        logout(getCookie(resultUser));
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }
}