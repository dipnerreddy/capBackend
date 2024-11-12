package com.capstonebackend.repository;

import com.capstonebackend.enity.BloodBankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankUserRepository extends JpaRepository<BloodBankUser, Long> {
    BloodBankUser findByEmail(String email);
    List<BloodBankUser> findBloodBankDTOByAddress(String address);
}

