package com.capstonebackend.controller;

import com.capstonebackend.dto.BloodBankInfo;
import com.capstonebackend.enity.BloodBankUser;
import com.capstonebackend.repository.*;
import com.capstonebackend.service.BloodBankService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DashbordController {

    @Autowired
    private BloodBankService bloodBankService;
    @Autowired
    private BloodBankUserRepository bloodBankUserRepository;

//    @GetMapping("/location")
//    @Operation(
//            summary = "",
//            description = "")
//    public ResponseEntity<List<BloodBankUser>> getRequestsByBloodBank(@RequestParam String address) {
//        String lowerCaseAddress = address.toLowerCase();
//        List<BloodBankUser> requests = bloodBankUserRepository.findBloodBankDTOByAddress(lowerCaseAddress);
//
//        if (requests.isEmpty()) {
//            return ResponseEntity.noContent().build();  // No requests found
//        }
//        return ResponseEntity.ok(requests);  // Return the list of requests
//    }

    @GetMapping("/search")
    @Operation(
            summary = "seraching blood bank in that area",
            description = "inputs: address and bloodType")
    public ResponseEntity<List<BloodBankInfo>> searchBloodBank(
            @RequestParam String address,
            @RequestParam String bloodType) {

        // Convert address to lowercase
        String normalizedAddress = address.toLowerCase();

        List<BloodBankInfo> result = bloodBankService.findBloodBanks(normalizedAddress, bloodType);
        return ResponseEntity.ok(result);
    }


}
