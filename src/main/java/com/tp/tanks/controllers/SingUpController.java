package com.tp.tanks.controllers;

import com.tp.tanks.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SingUpController {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGet(Model model) {

        return "signupGet";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public String signupPost(@RequestBody User user) {

        System.out.println("input username: " + user.getUsername() +
                           "; password: " + user.getPassword() +
                           "; id = " + user.getId());
        return "index";
    }
}