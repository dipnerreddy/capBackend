package com.capstonebackend.repository;

import com.capstonebackend.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findByPhoneNumber(String phoneNumber);
//    boolean findByPhoneNumber(String phoneNumber);
    boolean findByPassword(String password);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
