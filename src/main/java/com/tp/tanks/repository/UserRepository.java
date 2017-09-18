package com.tp.tanks.repository;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate ;


    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private User create(String username, String email, String password)
            throws org.springframework.dao.DuplicateKeyException, org.mariadb.jdbc.internal.util.dao.QueryException
    {

        String sql = "INSERT INTO user_tbl (username, email, password) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, bCryptPasswordEncoder.encode(password));
            return pst;
        }, keyHolder);
        return new User(keyHolder.getKey().longValue(), username, email, password);
    }


    public User save(User user)
            throws org.springframework.dao.DuplicateKeyException, org.mariadb.jdbc.internal.util.dao.QueryException {

        return create(user.getUsername(), user.getEmail(), user.getPassword());
    }


    public User signIn(User user) throws org.springframework.dao.EmptyResultDataAccessException {
        String sql = "SELECT * FROM user_tbl WHERE email = ?";

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

//How to get map
//        Map<String, Object> anotherUser2 = jdbcTemplate.queryForMap("select * from user_tbl where email = ?",
//                user.getEmail());
//
//        List<String> keys = new ArrayList<String>(anotherUser2.keySet());
//        for (String key: keys) {
//            System.out.println(key + ": " + anotherUser2.get(key));
//        }

