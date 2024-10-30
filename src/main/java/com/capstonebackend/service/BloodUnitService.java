package com.capstonebackend.service;

import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BloodUnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BloodUnitService {
    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    public BloodUnit addBloodUnit(BloodUnit bloodUnit) {
        return bloodUnitRepository.save(bloodUnit);
    }

    public void deleteExpiredUnits() {
        List<BloodUnit> expiredUnits = bloodUnitRepository.findAll()
                .stream()
                .filter(unit -> unit.getExpirationDate().isBefore(LocalDate.now()))
                .toList();
        bloodUnitRepository.deleteAll(expiredUnits);
    }
    @Transactional
    public void deleteBloodUnit(Long id) {
        Optional<BloodUnit> bloodUnit = bloodUnitRepository.findById(id);
        if (bloodUnit.isPresent()) {
            bloodUnitRepository.delete(bloodUnit.get());
        } else {
            throw new RuntimeException("Blood unit not found with id: " + id);
        }
    }

    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitRepository.findAll();
    }

    public BloodUnit save(BloodUnit bloodUnit) {
        // Perform any additional logic if needed
        return bloodUnitRepository.save(bloodUnit);
    }

}