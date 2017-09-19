package com.tp.tanks.repository;

import com.tp.tanks.model.User;
import com.tp.tanks.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate ;


    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User create(String username, String email, String password)
            throws DuplicateKeyException
    {

        final String sql = "INSERT INTO user_tbl (username, email, password) VALUES (?, ?, ?)";

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);
            return pst;
        }, keyHolder);
        return new User(keyHolder.getKey().longValue(), username, email, password);
    }

    public User findByEmail(String email) throws EmptyResultDataAccessException {

        final String sql = "SELECT * FROM user_tbl WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { email }, new UserMapper());
    }


    @SuppressWarnings("unused")
    public User getById(long userId) {
        final String sql = "SELECT id, username, email FROM user_tbl WHERE id = ?";

        final User user = jdbcTemplate.queryForObject(sql, new Object[] { userId }, new UserMapper());

        System.out.println("User From Base!!! " + user.getId() + "username = " + user.getUsername() + " email = " + user.getEmail());
        return user;
    }
}
