package com.capstonebackend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BloodBankDAOImpl implements BloodBankDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByBidAndBbName(String bid, String bbName) {
        String sql = "SELECT COUNT(*) FROM blood_units WHERE bid = ? AND bb_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{bid, bbName}, Integer.class);
        if( count != null && count > 0){
            return true;
        }
        else {
            return false;
        }
    }
}