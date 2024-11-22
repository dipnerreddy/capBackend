package com.capstonebackend.controller;

import com.capstonebackend.dao.UserDAO;
import com.capstonebackend.dto.BloodBankInfo;
import com.capstonebackend.dto.UserSearchDTO;
import com.capstonebackend.enity.User;
import com.capstonebackend.responce.ErrorResponse;
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
    private UserDAO userDAO;


    @GetMapping("/search")
    @Operation(
            summary = "seraching blood bank in that area",
            description = "inputs: address and bloodType")
    public ResponseEntity<List<BloodBankInfo>> searchBloodBank(
            @RequestParam String address,
            @RequestParam String bloodType) {
        String normalizedAddress = address.toLowerCase();
        List<BloodBankInfo> result = bloodBankService.findBloodBanks(normalizedAddress, bloodType);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/searchDonar")
    public ResponseEntity<?> searchUser(@RequestBody UserSearchDTO userSearchDTO) {
        String normalizedAddress = userSearchDTO.getAddress().toLowerCase();
        String bloodType = userSearchDTO.getBloodType();

        // Find the users by address and blood type
        List<User> list = userDAO.findByAddressAndBloodType(normalizedAddress, bloodType);

        if(list.size() != 0){
            return ResponseEntity.ok(list);
        }
        else {
            ErrorResponse errorResponse = new ErrorResponse("No users found with the given criteria.");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }

// kjfnakjwb fk

}
