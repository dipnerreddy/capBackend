package com.capstonebackend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean resetPassword(String phoneNumber, String password) {
        String sql = "UPDATE users SET password = ? WHERE phone_number = ?";
        int rowsAffected = jdbcTemplate.update(sql, password, phoneNumber);
        return rowsAffected > 0;
    }
}
