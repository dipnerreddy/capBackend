package com.capstonebackend.repository;

import com.capstonebackend.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDTORepository extends JpaRepository<UserDTO, Long> {
    List<UserDTO> findByBbName(String bbName);
}
