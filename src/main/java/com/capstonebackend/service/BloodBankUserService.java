package com.capstonebackend.service;


import com.capstonebackend.enity.BloodBankUser;
import com.capstonebackend.repository.BloodBankUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodBankUserService {

    @Autowired
    private BloodBankUserRepository bloodBankUserRepository;

    public BloodBankUser registerUser(BloodBankUser user) {
        return bloodBankUserRepository.save(user);
    }
    public BloodBankUser findByEmail(String email) {
        return bloodBankUserRepository.findByEmail(email);
    }
    public BloodBankUser login(String email, String password) {
        BloodBankUser user = bloodBankUserRepository.findByEmail(email);
        if (user != null) {
            System.out.println("User found: " + user.getEmail()); // Log found user
            if (user.getPassword().equals(password)) { // Use equals for string comparison
                return user; // Return user on successful login
            } else {
                System.out.println("Password mismatch for user: " + email); // Log password mismatch
            }
        } else {
            System.out.println("No user found with email: " + email); // Log no user found
        }
        return null; // Login failed
    }

}
