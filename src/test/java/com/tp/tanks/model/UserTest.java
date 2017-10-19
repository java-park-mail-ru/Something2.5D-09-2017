package com.tp.tanks.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class UserTest {

    User user;

    @Before
    public void setup() {
        user = new User(123L, "dracula", "pummol@mail.ru", "mypass");
    }

    @Test
    public void testId() {
        Long newId = 190L;
        user.setId(newId);
        assertEquals(user.getId(), newId);
    }

    @Test
    public void testUsername() {
        String newUserName = StringGenerator.generateString(10);
        user.setUsername(newUserName);
        assertEquals(user.getUsername(), newUserName);
    }

    @Test
    public void testEmail() {
        String newEmail = StringGenerator.generateString(7);
        newEmail += "@mail.ru";
        user.setEmail(newEmail);
        assertEquals(user.getEmail(), newEmail);
    }

    @Test
    public void testPassword() {
        String newPassword = StringGenerator.generateString(10);
        user.setPassword(newPassword);
        assertEquals(user.getPassword(), newPassword);
    }
}
