package com.capstonebackend.controller;

import com.capstonebackend.dao.BloodBankDAO;
import com.capstonebackend.enity.Bill;
import com.capstonebackend.repository.BillRepository;
import io.swagger.v3.oas.annotations.Operation;
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
    private BillRepository billRepository;
    @Autowired
    private BloodBankDAO bloodBankDAO;

    @PostMapping("/payment")
    @Operation(summary = "Payment request", description = "BloodBank portal can bill for a blood unit. \n inputs are patientName," +
            "patientNumber, bid, amount, bloodType, bbName")
    public ResponseEntity<String> makePayment(@RequestBody Bill bill) {
        String patientName = bill.getPatientName();
        String patientNumber = bill.getPatientNumber();
        String bid = bill.getBid();
        String amount = bill.getAmount();
        String bloodType = bill.getBloodType();
        String bbName = bill.getBbName();

        boolean exists = bloodBankDAO.existsByBidAndBbNameAndBloodGroup(bid, bbName, bloodType);
        if (exists) {
            bloodBankDAO.delete(bid, bbName);
            Bill newBill = new Bill();
            newBill.setPatientName(patientName);
            newBill.setPatientNumber(patientNumber);
            newBill.setBid(bid);
            newBill.setAmount(amount);
            newBill.setBloodType(bloodType);
            billRepository.save(newBill);
            return ResponseEntity.ok("Payment processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BID Not Present or Blood has expired");
        }
    }

}

