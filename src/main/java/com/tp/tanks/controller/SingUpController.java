package com.tp.tanks.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tp.tanks.model.User;
import com.tp.tanks.repository.NewUserRepository;
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


    @Autowired
    private NewUserRepository newUserRepository;

    private final static Logger logger = Logger.getLogger(SingUpController.class.getName());


    @RequestMapping(value = "/signUp", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signUp(@RequestBody User user, HttpSession sessioin) {

        // TODO ConstraintViolationException
        logger.info("Start Sign Up");

        if( !newUserRepository.checkRegistration(user) ) {
            System.out.println("This email already exists!!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        newUserRepository.saveUser(user);
//        userServise.save(user);
        System.out.println("IDDDD:::    " + user.getId());
        sessioin.setAttribute("userId", user.getId());
        System.out.println("Registration complete!");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/signIn", method = RequestMethod.POST,
                    consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashMap<String, String>> signIn(@RequestBody User user, HttpSession session)
    {
//        if( !newUserRepository.checkSignIn(user) ) {
//            System.out.println("Wrong signing in!");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<User> getProfile(HttpSession session)
    {
        Object userId = session.getAttribute("userId");
        if (userId == null){
            System.out.println("Not logged in!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        User user = userServise.getById((Long) userId);

        User user = newUserRepository.getUserById((Long)userId);

        System.out.println("id = " + user.getId() + "username = " + user.getUsername() + "; email = " + user.getPassword());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}