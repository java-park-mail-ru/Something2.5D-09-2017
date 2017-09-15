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
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<MultiValueMap<String, String>> signupPost(@RequestBody User user) {

        userServise.save(user);

        MultiValueMap<String, String> responseBody = new LinkedMultiValueMap<String, String>();
        responseBody.set("id", "id");
        responseBody.set("username", "username");

        MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<String, String>();
        responseHeaders.set("Content-Type", "application/json");
        responseHeaders.set("Accept", "application/json");

        return new ResponseEntity<MultiValueMap<String, String>>(responseBody, responseHeaders, HttpStatus.OK);
    }
}