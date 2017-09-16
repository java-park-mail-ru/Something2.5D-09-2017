package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.logging.Logger;

@Controller
public class SingUpController {

    @Autowired
    private UserService userServise;

    private final static Logger logger = Logger.getLogger(SingUpController.class.getName());

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGet(Model model) {

        return "signupGet";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashMap<String, String>> signupPost(@RequestBody User user) {

        // TODO ConstraintViolationException
        logger.info("Start Sign Up");

        HashMap<String, String> body = userServise.save(user);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}