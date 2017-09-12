package com.tp.tanks.controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

//    @RequestMapping("/")
//    public String index() {
//        return "Spring is working!!";
//    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}