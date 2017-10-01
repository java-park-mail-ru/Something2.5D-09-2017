package com.tp.tanks.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
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
        String newUserName = "newUserName";
        user.setUsername(newUserName);
        assertEquals(user.getUsername(), newUserName);
    }

    @Test
    public void testEmail() {
        String newEmail = "newEmail";
        user.setEmail(newEmail);
        assertEquals(user.getEmail(), newEmail);
    }

    @Test
    public void testPassword() {
        String newPassword = "newPassword";
        user.setPassword(newPassword);
        assertEquals(user.getPassword(), newPassword);
    }
}
