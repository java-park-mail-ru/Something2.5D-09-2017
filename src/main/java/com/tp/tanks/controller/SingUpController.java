package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@RestController
public class SingUpController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signUp(@RequestBody User user, HttpSession session) {

        final User saveUser = userService.save(user);
        if (saveUser == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        session.setAttribute("userId", saveUser.getId());
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signIn(@RequestBody User user, HttpSession session) {

        final User loginUser = userService.signIn(user);
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        session.setAttribute("userId", loginUser.getId());

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity logout(HttpSession session) {

        session.removeAttribute("userId");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getProfile(HttpSession session) {

        final Object id = session.getAttribute("userID");
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.getByid((Long) id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}