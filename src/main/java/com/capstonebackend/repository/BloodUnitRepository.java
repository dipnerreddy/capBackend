package com.capstonebackend.repository;

import com.capstonebackend.enity.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, Long> {
    Optional<BloodUnit> findById(Long id);

    // Method to find blood units by blood bank name
    List<BloodUnit> findByBbName(String bbName);
}
