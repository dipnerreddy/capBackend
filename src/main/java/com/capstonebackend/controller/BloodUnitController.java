package com.capstonebackend.controller;

import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.service.BloodUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloodunits")
public class BloodUnitController {
    @Autowired
    private BloodUnitService bloodUnitService;

    @PostMapping("/add")
    public ResponseEntity<?> addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        try {
            BloodUnit addedBloodUnit = bloodUnitService.addBloodUnit(bloodUnit);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBloodUnit);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding blood unit: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodUnit(@PathVariable Long id) {
        bloodUnitService.deleteBloodUnit(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/get")
    public ResponseEntity<List<BloodUnit>> getAllBloodUnits() {
        return ResponseEntity.ok(bloodUnitService.getAllBloodUnits());
    }
}