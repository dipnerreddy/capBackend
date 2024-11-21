package com.capstonebackend.dao;

import com.capstonebackend.enity.User;

import java.util.List;

public interface UserDAO {
    boolean resetPassword(String phoneNumber, String password);
    List<User> findByAddressAndBloodType(String address, String bloodType);
}
