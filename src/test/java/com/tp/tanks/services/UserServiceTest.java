package com.tp.tanks.services;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import com.tp.tanks.stubs.StringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.tp.tanks.stubs.UserFactory;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService service;


    @Test
    public void testSaveNewUser() {
        User user = UserFactory.create();
        User savedUser = service.save(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertNotEquals(user.getPassword(), savedUser.getPassword()); //  saved user must have an encrypted password
        assertNotNull(savedUser.getId());                             //  saved user must have an id
    }

    @Test
    public void testNoSaveDuplicateUser() {
        User user = UserFactory.create();
        service.save(user);
        User duplicateSavedUser = service.save(user);

        assertNull(duplicateSavedUser);
    }

    @Test
    public void testSignInUser() {
        User user = UserFactory.create();
        service.save(user);
        User detectedUser = service.signIn(user);

        assertEquals(user.getUsername(), detectedUser.getUsername());
        assertEquals(user.getEmail(), detectedUser.getEmail());
        assertNotEquals(user.getPassword(), detectedUser.getPassword());
        assertNotNull(detectedUser.getId());
    }

    @Test
    public void testSingInNotFoundUser() {
        User user = UserFactory.create();
        User another = UserFactory.create();
        service.save(user);
        User detectedUser = service.signIn(another);

        assertNull(detectedUser);
    }

    @Test
    public void testSignInBadPassword() {
        User user = UserFactory.create();
        service.save(user);

        user.setPassword(StringGenerator.generate(10));
        User detectedUser = service.signIn(user);

        assertNull(detectedUser);

    }

    @Test
    public void testGetById() {
        User user = UserFactory.create();
        User savedUser = service.save(user);
        User detectedUser = service.getByid(savedUser.getId());

        assertEquals(savedUser.getId(), detectedUser.getId());
        assertEquals(savedUser.getUsername(), detectedUser.getUsername());
        assertEquals(savedUser.getEmail(), detectedUser.getEmail());
        assertEquals(savedUser.getPassword(),detectedUser.getPassword());
    }
}
