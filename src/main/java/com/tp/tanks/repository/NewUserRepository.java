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


    public void save(User user) throws java.sql.SQLIntegrityConstraintViolationException, org.springframework.dao.DuplicateKeyException {
        String sql = "INSERT INTO user_tbl (username, email, password) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword());
        System.out.println("Created User = " + user.getUsername() + " email = " + user.getEmail() + " password = " + user.getPassword());
    }


    public User isSignedIn(User user) throws org.springframework.dao.EmptyResultDataAccessException {
        String sql = "SELECT id, username, email, password FROM user_tbl WHERE email = ?";

        User anotherUser = jdbcTemplate.queryForObject(sql, new Object[] { user.getEmail() }, new UserMapper());

        if(anotherUser == null) {
            return null;
        }

        if( !anotherUser.getPassword().equals( user.getPassword() ) ) {
            return null;
        }
        System.out.println("SignIn complete!");
        return anotherUser;
    }


    public User getById(long userId) {
        String sql = "SELECT id, username, email FROM user_tbl WHERE id = ?";

        User user = jdbcTemplate.queryForObject(sql, new Object[] { userId }, new UserMapper());

        System.out.println("User From Base!!! " + user.getId() + "username = " + user.getUsername() + " email = " + user.getEmail());
        return user;
    }
}

