package com.capstonebackend.controller;

import com.capstonebackend.enity.BloodBankUser;
import com.capstonebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DashbordController {

    @Autowired
    private BloodBankUserRepository bloodBankUserRepository;

    @GetMapping("/location")
    public ResponseEntity<List<BloodBankUser>> getRequestsByBloodBank(@RequestParam String address) {
        String lowerCaseAddress = address.toLowerCase();
        List<BloodBankUser> requests = bloodBankUserRepository.findBloodBankDTOByAddress(lowerCaseAddress);

        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();  // No requests found
        }
        return ResponseEntity.ok(requests);  // Return the list of requests
    }


}
