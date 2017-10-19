package com.tp.tanks.user;

import com.tp.tanks.model.User;
import com.tp.tanks.stubs.StringGenerator;
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

    User userStub;

    @Before
    public void setup() {
        userStub = new User(123L, "dracula", "pummol@mail.ru", "mypass");
    }

    @Test
    public void testId() {
        Long newId = 190L;
        userStub.setId(newId);
        assertEquals(userStub.getId(), newId);
    }

    @Test
    public void testUsername() {
        String newUserName = StringGenerator.generate(10);
        userStub.setUsername(newUserName);
        assertEquals(userStub.getUsername(), newUserName);
    }

    @Test
    public void testEmail() {
        String newEmail = StringGenerator.generate(7);
        newEmail += "@mail.ru";
        userStub.setEmail(newEmail);
        assertEquals(userStub.getEmail(), newEmail);
    }

    @Test
    public void testPassword() {
        String newPassword = StringGenerator.generate(10);
        userStub.setPassword(newPassword);
        assertEquals(userStub.getPassword(), newPassword);
    }
}
