package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final String defaultUsername = "userNameeee";
    private final String defaultPassword = "iampassword";


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

        final ResponseEntity result = restTemplate.postForEntity("/logout", requestEntity, String.class);
        assertEquals(httpStatus, result.getStatusCode());
    }


    private void getProfile(List<String> cookie, HttpStatus httpStatus) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        final HttpEntity requestEntity = new HttpEntity(requestHeaders);

        final ResponseEntity result = restTemplate.postForEntity("/profile", requestEntity, String.class);
        assertEquals(httpStatus, result.getStatusCode());
    }


    @Test
    public void testSignUp() {
        final String defaultEmail = "aaabbb@mail.com";
        final String defaultEmail2 = "sssxxx@mail.com";

        signUp(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.CREATED);
        signUp(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.FORBIDDEN);

        final List<String> cookie = signUp(new User(null, defaultUsername, defaultEmail2, defaultPassword), HttpStatus.CREATED);
        logout(cookie, HttpStatus.OK);

    }


    @Test
    public void testSignIn() {
        final String defaultEmail = "f123@mail.com";
        final String wrongEmail = "999@mail.com";
        final String wrongPassword = "33333";

        signIn(new User(null, defaultUsername, "", defaultPassword), HttpStatus.FORBIDDEN);
        signIn(new User(null, defaultUsername, defaultEmail, ""), HttpStatus.FORBIDDEN);

        List<String> cookie = signUp(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.CREATED);
        logout(cookie, HttpStatus.OK);

        cookie = signIn(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.OK);
        logout(cookie, HttpStatus.OK);

        signIn(new User(null, defaultUsername, wrongEmail, defaultPassword), HttpStatus.FORBIDDEN); //incorrect email
        signIn(new User(null, defaultUsername, wrongEmail, wrongPassword), HttpStatus.FORBIDDEN); //incorrect password
    }


    @Test
    public void testLogout() {
        final String defaultEmail = "456g@mail.com";
        final List<String> cookie = signUp(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.CREATED);

        logout(cookie, HttpStatus.OK);
        logout(cookie, HttpStatus.FORBIDDEN);//already logged out
    }

    @Test

    public void testGetProfile() {
        final String defaultEmail = "yablyk@mail.com";
        List<String> cookie = new ArrayList<>();
        getProfile(cookie, HttpStatus.FORBIDDEN);

        cookie = signUp(new User(null, defaultUsername, defaultEmail, defaultPassword), HttpStatus.CREATED);
        getProfile(cookie, HttpStatus.OK);
        logout(cookie, HttpStatus.OK);
        getProfile(cookie, HttpStatus.FORBIDDEN);
    }

}