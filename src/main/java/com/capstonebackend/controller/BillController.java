package com.capstonebackend.controller;

import com.capstonebackend.dao.BloodBankDAO;
import com.capstonebackend.dto.PaymentRequestDTO;
import com.capstonebackend.enity.Bill;
import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BillRepository;
import com.capstonebackend.service.BillService;
import com.capstonebackend.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BloodBankDAO bloodBankDAO;




    // here there is another problem. check properly

    @PostMapping("/payment")
    public ResponseEntity<String> makePayment(@RequestBody Bill bill) {
        String patientName = bill.getPatientName();
        String patientNumber = bill.getPatientNumber();
        String bid = bill.getBid();
        String amount = bill.getAmount(); // Fixed typo from 'ammount' to 'amount'
        String bloodType = bill.getBloodType(); // Change here to bloodType
        String bbName = bill.getBbName();

        // Check if the BID exists for the specified blood bank
        boolean exists = bloodBankDAO.existsByBidAndBbNameAndBloodGroup(bid, bbName, bloodType);
        if (exists) {
            bloodBankDAO.delete(bid, bbName);
            // Add to bills
            Bill newBill = new Bill();
            newBill.setPatientName(patientName);
            newBill.setPatientNumber(patientNumber);
            newBill.setBid(bid);
            newBill.setAmount(amount);
            newBill.setBloodType(bloodType); // Change here to bloodType
            // Save the bill (make sure you have a BillRepository for this)
            billRepository.save(newBill);
            return ResponseEntity.ok("Payment processed successfully");
        } else {
            // If BID does not exist, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BID Not Present or Blood has expired");
        }
    }




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

