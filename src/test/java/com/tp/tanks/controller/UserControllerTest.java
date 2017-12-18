package com.tp.tanks.controller;

import com.tp.tanks.models.User;
import com.tp.tanks.factories.UserFactory;
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
        parts.put("username", user.getUsername());
        parts.put("email", user.getEmail());
        parts.put("password", user.getPassword());
        parts.put("mouseControlEnabled", user.getMouseControlEnabled().toString());
        return parts;
    }

    private ResponseEntity<User> signUp(User user) {
        return restTemplate.postForEntity("/api/signUp", generateMap(user), User.class);
    }

    private ResponseEntity<User> signIn(User user) {
        return restTemplate.postForEntity("/api/signIn", generateMap(user), User.class);
    }

    private HttpEntity getEntity(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        return new HttpEntity(requestHeaders);
    }

    private ResponseEntity<User> logout(List<String> cookie) {
        return restTemplate.exchange("/api/logout", HttpMethod.GET, getEntity(cookie), User.class);
    }

    private ResponseEntity<User> getProfile(List<String> cookie) {
        return restTemplate.exchange("/api/profile", HttpMethod.GET, getEntity(cookie), User.class);
    }

    @Test
    public void testSignUp() {
        final User user = UserFactory.create();

        ResponseEntity<User> responseUser = signUp(user);
        assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());

        responseUser = logout(getCookie(responseUser));
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());
    }

    @Test
    public void testSignUpConflict() {
        final User user = UserFactory.create();

        ResponseEntity<User> responseUser = signUp(user);
        assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());

        responseUser = signUp(user);
        assertEquals(HttpStatus.FORBIDDEN, responseUser.getStatusCode());
    }

    @Test
    public void testSignIn() {
        final User firstUser = UserFactory.create();

        ResponseEntity<User> responseUser = signIn(firstUser);
        assertEquals(HttpStatus.FORBIDDEN, responseUser.getStatusCode());

        responseUser = signUp(firstUser);
        logout(getCookie(responseUser));

        responseUser = signIn(firstUser);
        responseUser = logout(getCookie(responseUser));
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());
    }

    @Test
    public void testLogout() {
        final User user = UserFactory.create();

        ResponseEntity<User> responseUser = signUp(user);

        responseUser = logout(getCookie(responseUser));
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());

        responseUser = logout(getCookie(responseUser));
        assertEquals(HttpStatus.FORBIDDEN, responseUser.getStatusCode());
    }

    @Test
    public void testGetProfile() {
        final List<String> cookie = new ArrayList<>();
        ResponseEntity<User> responseUser = getProfile(cookie);
        assertEquals(HttpStatus.UNAUTHORIZED, responseUser.getStatusCode());

        final User user = UserFactory.create();

        responseUser = signUp(user);
        responseUser = getProfile(getCookie(responseUser));
        assertEquals(HttpStatus.OK, responseUser.getStatusCode());

        logout(getCookie(responseUser));
        responseUser = getProfile(getCookie(responseUser));
        assertEquals(HttpStatus.UNAUTHORIZED, responseUser.getStatusCode());
    }
}