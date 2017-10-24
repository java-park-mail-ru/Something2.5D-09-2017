package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/signUp", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signUp(@RequestBody User user, HttpSession session) {
        logger.info("[signUp] INPUT:  username = " + user.getUsername() + " email = " + user.getEmail());
        final User saveUser = userService.save(user);

        if (saveUser == null) {
            logger.error("[signUp] saveUser == null");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.info("[signUp] OUTPUT: username = " + user.getUsername() + " email = " + user.getEmail());

        session.setAttribute("userId", saveUser.getId());
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signIn(@RequestBody User user, HttpSession session) {

        logger.info("[signIn] INPUT:  username = " + user.getUsername() + " email = " + user.getEmail());

        final User loginUser = userService.signIn(user);
        if (loginUser == null) {
            logger.error("[signIn] loginUser == null");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.info("[signIn] OUTPUT: username = " + loginUser.getUsername() + " email = " + loginUser.getEmail());

        session.setAttribute("userId", loginUser.getId());

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity logout(HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        logger.info("[logout]");
        session.removeAttribute("userId");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getProfile(HttpSession session) {

        final Object id = session.getAttribute("userId");
        if (id == null) {
            logger.info("[getProfile] user not found in session");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        final User user = userService.getByid((Long) id);
        logger.info("[getProfile] OUTPUT: username = " + user.getUsername() + " email = " + user.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity index() {

        final Map<String, String> body = new HashMap<>();
        body.put("value", "index");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}