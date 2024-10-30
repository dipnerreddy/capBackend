package com.capstonebackend.service;

import com.capstonebackend.dto.PaymentRequestDTO;
import com.capstonebackend.enity.Bill;
import com.capstonebackend.enity.BloodUnit;
import com.capstonebackend.repository.BillRepository;
import com.capstonebackend.repository.BloodUnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    @Autowired
    private BillRepository billRepository;

//    @Transactional
//    public String processPayment(PaymentRequestDTO paymentRequest) {
//        // Check if BID exists
//        BloodUnit bloodUnit = bloodUnitRepository.findByBid(paymentRequest.getBid());
//        if (bloodUnit == null) {
//            return "BID not found"; // Or throw an exception
//        }
//
//        // Create a new Bill entry
//        Bill bill = new Bill();
//        bill.setPatientName(paymentRequest.getPatientName());
//        bill.setPatientNumber(paymentRequest.getPatientNumber());
//        bill.setAmount(paymentRequest.getAmount());
//        bill.setBid(paymentRequest.getBid());
//        bill.setBloodGroup(paymentRequest.getBloodGroup());
//        billRepository.save(bill);
//
//        // Delete the blood unit
//        bloodUnitRepository.delete(bloodUnit);
//
//        return "Payment processed successfully";
//    }
}