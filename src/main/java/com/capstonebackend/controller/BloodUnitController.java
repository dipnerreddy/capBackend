package com.capstonebackend.controller;

import com.capstonebackend.dao.BloodBankDAO;
import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BloodUnitRepository;
import com.capstonebackend.service.BloodUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bloodunits")
public class BloodUnitController {
    @Autowired
    private BloodUnitService bloodUnitService;
    @Autowired
    private BloodUnitRepository bloodUnitRepository;
    @Autowired
    private BloodBankDAO bloodBankDAO;

    @PostMapping("/add")
    public ResponseEntity<?> addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        // Ensure your BloodUnit class has the appropriate fields
        String bloodType = bloodUnit.getBloodType();
        String bid = bloodUnit.getBid();
        String bbName = bloodUnit.getBbName();
        int quantity = bloodUnit.getQuantity();
        LocalDate expirationDate = bloodUnit.getExpirationDate();


        boolean T=bloodBankDAO.existsByBidAndBbName(bid,bbName);
        if(T==false){
            BloodUnit newBloodUnit = new BloodUnit(bloodType,quantity,expirationDate, bid, bbName);
            bloodUnitRepository.save(newBloodUnit);

            return ResponseEntity.ok("Blood Unit added successfully");
        }
        else if(T==true){
            return ResponseEntity.status(400).body("BID Present");
        }
        return ResponseEntity.status(404).body("Somthing Wrong");
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