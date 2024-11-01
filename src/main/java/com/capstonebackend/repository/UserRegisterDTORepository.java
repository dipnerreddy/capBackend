package com.capstonebackend.repository;

import com.capstonebackend.dto.UserRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterDTORepository extends JpaRepository<UserRegisterDTO,Long> {
}
