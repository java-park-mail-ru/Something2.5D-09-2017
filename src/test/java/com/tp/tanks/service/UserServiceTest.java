package com.tp.tanks.service;

import com.tp.tanks.model.User;
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
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    private final String defaultUsername = "yoyyouUser";
    private final String defaultPassword = "yoyPass";

    @Test
    public void testSave() {
        final String defaultEmail = "yoy@mail.ru";

        User resultUser = userService.save(new User(null, defaultUsername, defaultEmail, defaultPassword));
        assertNotNull(resultUser);
        assertEquals(defaultEmail, resultUser.getEmail());

        testSaveConflict(resultUser);
    }

    private void testSaveConflict(User user) {
        User resultUser = userService.save(user);
        assertNull(resultUser);
    }

    @Test
    public void testSignIn() {
        final String defaultEmail = "dancingUser@mail.ru";

        User toSignIn = new User(null, defaultUsername, defaultEmail, defaultPassword);
        User resultSignIn = userService.signIn(toSignIn);
        assertNull(resultSignIn);

        userService.save(toSignIn);
        resultSignIn = userService.signIn(toSignIn);
        assertNotNull(resultSignIn);
        assertEquals(defaultEmail, resultSignIn.getEmail());
    }

    @Test
    public void getById() {
        final String defaultEmail = "IDDuSER@mail.ru";

        User saved = userService.save(new User(null, defaultUsername, defaultEmail, defaultPassword));
        assertNotNull(saved.getId());

        User wasFound = userService.getByid(saved.getId());
        assertNotNull(wasFound);
        assertEquals(saved.getId(), wasFound.getId());
    }
}
