package com.capstonebackend.service;

import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BloodUnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BloodUnitService {
    @Autowired
    private BloodUnitRepository bloodUnitRepository;

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

    public Map<String, Integer> getBloodUnitsByBankName(String bbName) {
        List<BloodUnit> bloodUnits = bloodUnitRepository.findByBbName(bbName);
        Map<String, Integer> bloodUnitCounts = new HashMap<>();

        // Accumulate quantities by blood type
        for (BloodUnit unit : bloodUnits) {
            bloodUnitCounts.put(
                    unit.getBloodType(),
                    bloodUnitCounts.getOrDefault(unit.getBloodType(), 0) + unit.getQuantity()
            );
        }
        return bloodUnitCounts;
    }
}