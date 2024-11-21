package com.capstonebackend.repository;

import com.capstonebackend.dto.UserDTO;
import com.capstonebackend.dto.UserLastDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLastDonationRepo  extends JpaRepository<UserLastDonation, Long> {
    UserLastDonation findByPhoneNumber(String phoneNumber);
}
