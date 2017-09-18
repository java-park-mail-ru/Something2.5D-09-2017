package com.tp.tanks.service;

import com.tp.tanks.model.User;
import com.tp.tanks.repository.NewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserService {

    @Autowired
    private NewUserRepository userRepository;

    public User save(User user) {

        try {
            return userRepository.save(user);
        }
        catch (org.springframework.dao.DuplicateKeyException err) {
            System.out.println("[DuplicateKeyException] " + err);
            return null;
        }
        catch (org.mariadb.jdbc.internal.util.dao.QueryException err) {
            System.out.println("[QueryException] " + err);
            return null;
        }
    }

//    public User signIn(User user) {
//
//        return User
//    }

//    @Transactional
//    public User getById(Long id)
//    {
//        return userRepository.getOne(id);
//    }

}
