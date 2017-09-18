package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@RestController
public class SingUpController {

    @Autowired
    private UserService userService;

    private final static Logger logger = Logger.getLogger(SingUpController.class.getName());


    @RequestMapping(value = "/signUp", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signUp(@RequestBody User user, HttpSession sessioin) {

        // TODO ConstraintViolationException
        logger.info("Start Sign Up");

        User saveUser = userService.save(user);
        if (saveUser == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }

        sessioin.setAttribute("user", saveUser);
        System.out.println("Registration complete!");
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signIn(@RequestBody User user, HttpSession session)
    {
//        User result = null;
//        try {
//            result = userService.signIn(user);
//        }
//        catch(org.springframework.dao.EmptyResultDataAccessException e) {
//            System.out.println("SignedIn exceprion@@ = " + e);
//        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getProfile(HttpSession session)
    {
        Object user = session.getAttribute("user");
        if (user == null) {
            System.out.println("Not logged in!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

////        User user = userServise.getById((Long) userId);
//
//        User user = userService.getById((Long)userId);
//
//        System.out.println("id = " + user.getId() + "username = " + user.getUsername() + "; email = " + user.getPassword());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}