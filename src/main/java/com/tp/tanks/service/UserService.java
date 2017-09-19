package com.tp.tanks.service;

import com.tp.tanks.model.User;
import com.tp.tanks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(User user) {

        try {

            return userRepository.create(user.getUsername(), user.getEmail(), bCryptEncoder.encode(user.getPassword()));
        }
        catch (DuplicateKeyException err) {
            System.out.println(err.toString());
            return null;
        }
    }

    public User signIn(User user) {

        try {

            final User findUser = userRepository.findByEmail(user.getEmail());

            if (!bCryptEncoder.matches(user.getPassword(), findUser.getPassword())) {
                System.out.println("password not equal");
                return null;
            }

            return findUser;

        } catch (EmptyResultDataAccessException err) {

            System.out.println(err.toString());
            System.out.println("not found by email");
            return null;
        }
    }
}

//    @Transactional
//    public User getById(Long id)
//    {
//        return userRepository.getOne(id);
//    }
