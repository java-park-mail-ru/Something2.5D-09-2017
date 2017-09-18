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
    public ResponseEntity<User> signUp(@RequestBody User user, HttpSession session) {

        User saveUser = userService.save(user);
        if (saveUser == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }

        session.setAttribute("user", saveUser);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signIn(@RequestBody User user, HttpSession session)
    {
        User loginUser = userService.signIn(user);
        if (loginUser == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }

        session.setAttribute("user", loginUser);

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("user");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getProfile(HttpSession session)
    {
        Object user = session.getAttribute("user");
        if (user == null) { return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}