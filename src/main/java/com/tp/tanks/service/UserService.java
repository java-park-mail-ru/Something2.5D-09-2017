package com.tp.tanks.service;

import com.tp.tanks.model.User;
import com.tp.tanks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
    public void save(User user) {
        System.out.println("[UserService] username: " + user.getUsername() + "; password: " + user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User saving_user = userRepository.save(user);
        System.out.println("[UserService] saving user: username: " + saving_user.getUsername() + "; " +
                "password: " + saving_user.getPassword() +
                "; id = " + saving_user.getId());
    }
}
