package com.tp.tanks.services;

import com.tp.tanks.models.User;
import com.tp.tanks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder cryptEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder cryptEncoder) {
        this.userRepository = userRepository;
        this.cryptEncoder = cryptEncoder;
    }

    public User save(User user) {

        try {

            final Boolean mouseControlEnabled;
            if (user.getMouseControlEnabled() == null) {
                mouseControlEnabled = false;
            } else {
                mouseControlEnabled = user.getMouseControlEnabled();
            }

            return userRepository.create(user.getUsername(), user.getEmail(),
                    cryptEncoder.encode(user.getPassword()), mouseControlEnabled);
        } catch (DuplicateKeyException err) {
            return null;
        }
    }

    public User signIn(User user) {

        try {
            final User findUser = userRepository.findByEmail(user.getEmail());

            if (!cryptEncoder.matches(user.getPassword(), findUser.getPassword())) {
                return null;
            }

            return findUser;
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    public User getById(Long id) {

        try {
            return userRepository.getById(id);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    public Boolean updateMouseControlEnabled(Long userId, Boolean mouseControlEnabled) {
        if (userId == null || mouseControlEnabled == null) {
            return false;
        }
        try {
            userRepository.updateMouseControlEnabled(userId, mouseControlEnabled);
            return true;
        } catch (EmptyResultDataAccessException err) {
            return false;
        }
    }
}
