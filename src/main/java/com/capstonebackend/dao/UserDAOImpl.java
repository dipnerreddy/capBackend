package com.capstonebackend.dao;

import com.capstonebackend.dto.UserLastDonation;
import com.capstonebackend.enity.User;
import com.capstonebackend.repository.UserLastDonationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserLastDonationRepo userLastDonation;

    @Override
    public boolean resetPassword(String phoneNumber, String password) {
        String sql = "UPDATE bb_user SET password = ? WHERE phone_number = ?";
        int rowsAffected = jdbcTemplate.update(sql, password, phoneNumber);
        return rowsAffected > 0;
    }

//    @Override
//    public List<User> findByAddressAndBloodType(String address, String bloodType) {
//        // Log the parameters to verify they are correct
//        System.out.println("Address: " + address);
//        System.out.println("Blood Type: " + bloodType);
//
//        // Modify the query to be case-insensitive using LOWER()
//        String sql = "SELECT * FROM bb_user WHERE LOWER(address) = LOWER(?) AND LOWER(blood_type) = LOWER(?)";
//
//        List<User> users = jdbcTemplate.query(sql, new Object[]{address, bloodType}, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                user.setId(rs.getLong("user_id"));
//                user.setAddress(rs.getString("address"));
//                user.setBloodType(rs.getString("blood_type"));
//                user.setPassword(rs.getString("password"));
//                user.setPhoneNumber(rs.getString("phone_number"));
//                user.setUserName(rs.getString("user_name"));
//                return user;
//            }
//        });
//
//        return users;
//    }



    @Override
    public List<User> findByAddressAndBloodType(String address, String bloodType) {
        String sql = "SELECT * FROM bb_user WHERE address = ? AND blood_type = ?";

        List<User> users = jdbcTemplate.query(sql, new Object[]{address, bloodType}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setAddress(rs.getString("address"));
                user.setBloodType(rs.getString("blood_type"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setUserName(rs.getString("user_name"));
                return user;
            }
        });

        // After getting users, check their donation date and filter them based on that
        List<User> eligibleUsers = new ArrayList<>();
        for (User user : users) {
            UserLastDonation existingUser = userLastDonation.findByPhoneNumber(user.getPhoneNumber());
            if (existingUser != null) {
                String lastDonationDateString = existingUser.getDate(); // The date is stored as a string in the database, like "2023-07-01"
                LocalDate lastDonationDate = LocalDate.parse(lastDonationDateString); // Convert string to LocalDate

                // Calculate the days difference from today
                long daysSinceLastDonation = ChronoUnit.DAYS.between(lastDonationDate, LocalDate.now());

                // Add user to eligible list if the donation was more than 90 days ago
                if (daysSinceLastDonation > 90) {
                    eligibleUsers.add(user);
                }
            }
        }

        return eligibleUsers;
    }





}
