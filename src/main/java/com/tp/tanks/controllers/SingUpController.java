package com.tp.tanks.controllers;

import com.tp.tanks.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SingUpController {


    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String singUp(Model model) {
        model.addAttribute("userForm", new User());

//        return "registration";
        return "sdfgdf";
    }

}
