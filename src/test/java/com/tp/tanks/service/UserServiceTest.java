package com.tp.tanks.service;

import com.tp.tanks.model.User;
import com.tp.tanks.model.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    UserService userService;

    private final String defaultUsername = "yoyyouUser";
    private final String defaultPassword = "yoyPass";

    private User save(User user) {
        return userService.save(user);
    }

    private User signIn(User user) {
        return userService.signIn(user);
    }

    private User getById(Long id) {
        return userService.getByid(id);
    }

    @Test
    public void testSave() {
        User testUser = UserGenerator.generateUser();

        User responseUser = save(testUser);
        assertNotNull(responseUser);
        assertEquals(responseUser.getEmail(), testUser.getEmail());
    }

    @Test
    public void testSaveConflict() {
        User testUser = UserGenerator.generateUser();

        User responseUser = save(testUser);
        assertNotNull(responseUser);

        responseUser = save(testUser);
        assertNull(responseUser);
    }

    @Test
    public void testSignIn() {
        User testUser = UserGenerator.generateUser();

        User responseUser = signIn(testUser);
        assertNull(responseUser);

        save(testUser);
        responseUser = signIn(testUser);
        assertNotNull(responseUser);
        assertEquals(testUser.getEmail(), responseUser.getEmail());
    }

    @Test
    public void getById() {
        User testUser = UserGenerator.generateUser();

        User responseUser = save(testUser);

        User wasFound = getById(responseUser.getId());
        assertNotNull(wasFound);
        assertEquals(responseUser.getId(), wasFound.getId());
    }
}