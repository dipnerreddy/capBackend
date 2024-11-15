package com.capstonebackend.repository;

import com.capstonebackend.enity.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, Long> {
    Optional<BloodUnit> findById(Long id);

    // Method to find blood units by blood bank name
    List<BloodUnit> findByBbName(String bbName);

    @Query("SELECT COUNT(b) FROM BloodUnit b WHERE b.bbName = :bbName AND b.bloodType = :bloodType")
    int countByBbNameAndBloodType(@Param("bbName") String bbName, @Param("bloodType") String bloodType);

    @Query("SELECT DISTINCT b.bbName FROM BloodUnit b WHERE b.bloodType = :bloodType")
    List<String> findBbNamesByBloodType(@Param("bloodType") String bloodType);

}
