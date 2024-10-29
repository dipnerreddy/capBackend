package com.capstonebackend.controller;

import com.capstonebackend.enity.Bill;
import com.capstonebackend.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/generateBill")
    public ResponseEntity<?> generateBill(@RequestBody Bill bill) {
        try {
            // Logic to handle bill generation using BID
            Bill generatedBill = billService.generateBill(bill);
            return ResponseEntity.status(HttpStatus.CREATED).body(generatedBill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating bill: " + e.getMessage());
        }
    }
}