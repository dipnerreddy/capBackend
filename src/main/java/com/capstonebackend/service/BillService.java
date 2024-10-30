package com.capstonebackend.service;

import com.capstonebackend.enity.Bill;
import com.capstonebackend.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill generateBill(Bill bill) {
//        bill.setCreatedAt(LocalDateTime.now());
        return billRepository.save(bill);
    }
}