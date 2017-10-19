package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.stubs.UserStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<String> getCookie(ResponseEntity<User> user) {
        List<String> cookie = user.getHeaders().get("Set-Cookie");

        if(cookie == null) {
            cookie =  new ArrayList<>();
        }
        return cookie;
    }

    private Map<String, String> generateMap(User user) {
        final Map<String, String> parts = new HashMap<>();
        parts.put("id", null);
        parts.put("username", user.getUsername());
        parts.put("email", user.getEmail());
        parts.put("password", user.getPassword());
        return parts;
    }

    private ResponseEntity<User> signUp(User user) {
        return restTemplate.postForEntity("/signUp", generateMap(user), User.class);
    }

    private ResponseEntity<User> signIn(User user) {
        return restTemplate.postForEntity("/signIn", generateMap(user), User.class);
    }

    private HttpEntity getEntity(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        return new HttpEntity(requestHeaders);
    }

    private ResponseEntity<User> logout(List<String> cookie) {
        return restTemplate.exchange("/logout", HttpMethod.GET, getEntity(cookie), User.class);
    }

    private ResponseEntity<User> getProfile(List<String> cookie) {
        return restTemplate.exchange("/profile", HttpMethod.GET, getEntity(cookie), User.class);
    }

    @Test
    public void testSignUp() {
        final User testUser = UserStub.create();

        ResponseEntity<User> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());
    }

    @Test
    public void testSignUpConflict() {
        final User testUser = UserStub.create();

        ResponseEntity<User> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = signUp(testUser);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testSignIn() {
        final User firstUser = UserStub.create();

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
        final User testUser = UserStub.create();

        ResponseEntity<User> resultUser = signUp(testUser);

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testGetProfile() {
        final List<String> cookie = new ArrayList<>();
        ResponseEntity<User> resultUser = getProfile(cookie);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());

        final User testUser = UserStub.create();

        resultUser = signUp(testUser);
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        logout(getCookie(resultUser));
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }
}