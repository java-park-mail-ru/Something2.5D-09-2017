package com.tp.tanks.repository;

import com.tp.tanks.models.User;
import com.tp.tanks.services.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User create(String username, String email, String password, Boolean mouseControlEnabled)
            throws DuplicateKeyException {

        final String sql = "INSERT INTO user_tbl (username, email, password, mouse_control_enabled) VALUES (?, ?, ?, ?)";
        final int usernameIdx = 1;
        final int emailIdx = 2;
        final int passwordIdx = 3;
        final int mouseControlEnabledIdx = 4;
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(usernameIdx, username);
            pst.setString(emailIdx, email);
            pst.setString(passwordIdx, password);
            pst.setBoolean(mouseControlEnabledIdx, mouseControlEnabled);
            return pst;
        }, keyHolder);
        return new User(keyHolder.getKey().longValue(), username, email, password, mouseControlEnabled);
    }

    public User findByEmail(String email) throws EmptyResultDataAccessException {
        final String sql = "SELECT * FROM user_tbl WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserMapper());
    }


    public User getById(long userId) throws EmptyResultDataAccessException {
        final String sql = "SELECT * FROM user_tbl WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserMapper());
    }
}
