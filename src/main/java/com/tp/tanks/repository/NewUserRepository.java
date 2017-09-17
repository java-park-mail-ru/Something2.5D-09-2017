package com.tp.tanks.repository;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NewUserRepository {
    private final JdbcTemplate jdbcTemplate ;


    @Autowired
    public NewUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void saveUser(User user) {
        String sql = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getId(), user.getUsername(), user.getEmail(), user.getPassword());

        System.out.println("Created User = " + user.getUsername() + " email = " + user.getEmail());
    }


    public boolean checkRegistration(User user) {
        //Проверка уникальности email
        String sql = "SELECT id, username, email FROM users WHERE email = ?";

        User anotherUser = jdbcTemplate.queryForObject(sql, new Object[] { user.getEmail() }, new UserMapper());

        if(anotherUser == null) {
            return false;
        }
        return true;
    }


    public boolean checkSignIn(User user) {
        String sql = "SELECT id, username, email FROM users WHERE email = ?";

        User anotherUser = jdbcTemplate.queryForObject(sql, new Object[] { user.getEmail() }, new UserMapper());

        if(anotherUser == null) {
            return false;
        }

//        if( !anotherUser.getPassword().equals( user.getPassword() ) ) {
//            return false;
//        }
        return true;
    }


    public User getUserById(long userId) {
        String sql = "SELECT id, username, email FROM users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(sql, new Object[] { userId }, new UserMapper());

        System.out.println("User From Base!!! " + user.getId() + "username = " + user.getUsername() + " email = " + user.getEmail());
        return user;
    }
}

