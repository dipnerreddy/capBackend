package com.capstonebackend.repository;

import com.capstonebackend.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
