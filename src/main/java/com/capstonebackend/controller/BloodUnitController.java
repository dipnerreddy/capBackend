package com.capstonebackend.controller;

import com.capstonebackend.dao.BloodBankDAO;
import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BloodUnitRepository;
import com.capstonebackend.service.BloodUnitService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bloodunits")
public class BloodUnitController {
    @Autowired
    private BloodUnitService bloodUnitService;
    @Autowired
    private BloodUnitRepository bloodUnitRepository;
    @Autowired
    private BloodBankDAO bloodBankDAO;


    @GetMapping("/piechart/{bbName}")
    @Operation(
            summary = "piechart representation of data",
            description = "using this api, we are representing the data in the form of piechart.\n inputs: bbName ")
    public Map<String, Integer> getBloodUnitsByBloodBankName(@PathVariable String bbName) {
        return bloodUnitService.getBloodUnitsByBankName(bbName);
    }

    @PostMapping("/add")
    @Operation(
            summary = "adding a new bloodunit",
            description = "adding the new stock of blood which got donated recently")
    public ResponseEntity<?> addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        String bloodType = bloodUnit.getBloodType();
        String bid = bloodUnit.getBid();
        String bbName = bloodUnit.getBbName();
        int quantity = bloodUnit.getQuantity();
        LocalDate expirationDate = bloodUnit.getExpirationDate();


        boolean T=bloodBankDAO.existsByBidAndBbName(bid,bbName);
        if(!T){
            BloodUnit newBloodUnit = new BloodUnit(bloodType,quantity,expirationDate, bid, bbName);
            bloodUnitRepository.save(newBloodUnit);

            return ResponseEntity.ok("Blood Unit added successfully");
        }
        else if(T){
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