package com.tp.tanks.controller;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SingUpController {

    @Autowired
    private UserService userServise;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGet(Model model) {

        return "signupGet";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public String signupPost(@RequestBody User user) {

        userServise.save(user);

        return "index";
    }
}