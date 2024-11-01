package com.capstonebackend.dao;

public interface UserDAO {
    boolean resetPassword(String phoneNumber, String password);
}
