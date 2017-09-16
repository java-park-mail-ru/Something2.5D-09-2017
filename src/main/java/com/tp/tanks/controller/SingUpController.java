package com.tp.tanks.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.logging.Logger;

@RestController
public class SingUpController {

    @Autowired
    private UserService userServise;

    private final static Logger logger = Logger.getLogger(SingUpController.class.getName());

    @RequestMapping(value = "/signUp", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signupPost(@RequestBody User user, HttpSession sessioin) {

        // TODO ConstraintViolationException
        logger.info("Start Sign Up");

        userServise.save(user);
        sessioin.setAttribute("userId", user.getId());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashMap<String, String>> signIn(@RequestBody User user, HttpSession session)
    {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> getProfile(HttpSession session)
    {
        Object userID = session.getAttribute("userId");
        if (userID == null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userServise.getById((Long) userID);
        User serializableSUser = new User();

        serializableSUser.setId(user.getId());
        serializableSUser.setUsername(user.getUsername());
        serializableSUser.setPassword(user.getPassword());


        System.out.println("username = " + serializableSUser.getUsername() + "; password = " + serializableSUser.getPassword());

        return new ResponseEntity<>(serializableSUser, HttpStatus.OK);
    }
}