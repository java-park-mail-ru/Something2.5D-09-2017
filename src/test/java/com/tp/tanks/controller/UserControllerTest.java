package com.tp.tanks.controller;

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

    private List<String> getCookie(ResponseEntity<UserStub> user) {
        List<String> cookie = user.getHeaders().get("Set-Cookie");

        if(cookie == null) {
            cookie =  new ArrayList<>();
        }
        return cookie;
    }

    private Map<String, String> generateMap(UserStub user) {
        final Map<String, String> parts = new HashMap<>();
        parts.put("username", user.getUsername());
        parts.put("email", user.getEmail());
        parts.put("password", user.getPassword());
        return parts;
    }

    private ResponseEntity<UserStub> signUp(UserStub user) {
        return restTemplate.postForEntity("/signUp", generateMap(user), UserStub.class);
    }

    private ResponseEntity<UserStub> signIn(UserStub user) {
        return restTemplate.postForEntity("/signIn", generateMap(user), UserStub.class);
    }

    private HttpEntity getEntity(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        return new HttpEntity(requestHeaders);
    }

    private ResponseEntity<UserStub> logout(List<String> cookie) {
        return restTemplate.exchange("/logout", HttpMethod.GET, getEntity(cookie), UserStub.class);
    }

    private ResponseEntity<UserStub> getProfile(List<String> cookie) {
        return restTemplate.exchange("/profile", HttpMethod.GET, getEntity(cookie), UserStub.class);
    }

    @Test
    public void testSignUp() {
        final UserStub testUser = UserStub.create();

        ResponseEntity<UserStub> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());
    }

    @Test
    public void testSignUpConflict() {
        final UserStub testUser = UserStub.create();

        ResponseEntity<UserStub> resultUser = signUp(testUser);
        assertEquals(HttpStatus.CREATED, resultUser.getStatusCode());

        resultUser = signUp(testUser);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testSignIn() {
        final UserStub firstUser = UserStub.create();

        ResponseEntity<UserStub> resultUser = signIn(firstUser);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());

        resultUser = signUp(firstUser);
        logout(getCookie(resultUser));

        resultUser = signIn(firstUser);
        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());
    }

    @Test
    public void testLogout() {
        final UserStub testUser = UserStub.create();

        ResponseEntity<UserStub> resultUser = signUp(testUser);

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        resultUser = logout(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }

    @Test
    public void testGetProfile() {
        final List<String> cookie = new ArrayList<>();
        ResponseEntity<UserStub> resultUser = getProfile(cookie);
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());

        final UserStub testUser = UserStub.create();

        resultUser = signUp(testUser);
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.OK, resultUser.getStatusCode());

        logout(getCookie(resultUser));
        resultUser = getProfile(getCookie(resultUser));
        assertEquals(HttpStatus.FORBIDDEN, resultUser.getStatusCode());
    }
}