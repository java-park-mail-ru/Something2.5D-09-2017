package com.tp.tanks.service;

import com.tp.tanks.model.User;
import com.tp.tanks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public HashMap<String, String> save(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User saving_user = userRepository.save(user);

        HashMap<String, String> body = new HashMap<>();
        body.put("id", saving_user.getId().toString());
        body.put("username", saving_user.getUsername());

        return body;
    }
}
