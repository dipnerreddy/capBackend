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

    @Override
    public boolean delete(String bid, String bbName) {
        String sql = "DELETE FROM blood_units WHERE bid = ? AND bb_name = ?";
        int rowsAffected = jdbcTemplate.update(sql, bid, bbName);
        // If rowsAffected is greater than 0, deletion was successful
        return rowsAffected > 0;
    }
    @Override
    public boolean existsByBidAndBbNameAndBloodGroup(String bid, String bbName, String bloodType) {
        String sql = "SELECT COUNT(*) FROM blood_units WHERE bid = ? AND bb_name = ? AND blood_type = ?";
        System.out.println("Executing SQL: " + sql + " with parameters: " + bid + ", " + bbName + ", " + bloodType);
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{bid, bbName, bloodType}, Integer.class);
        return count != null && count > 0;
    }


}