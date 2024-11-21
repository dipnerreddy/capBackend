package com.capstonebackend.controller;

import com.capstonebackend.dto.LoginRequestDTO;
import com.capstonebackend.enity.BloodBankUser;
import com.capstonebackend.service.BloodBankUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bloodBank")
public class BloodBankUserController {
    @Autowired
    private BloodBankUserService bloodBankUserService;

    @PostMapping("/register")
    @Operation(
            summary = "blood bank Registration",
            description = "inputs: " +
                    "bbName, email, address, password")
    public ResponseEntity<String> registerUser(@RequestBody BloodBankUser user) {
        BloodBankUser existingUser = bloodBankUserService.findByEmail(user.getEmail());

        if (existingUser != null) {
            return ResponseEntity.status(409).body("User already exists with this email");
        }

        BloodBankUser registeredUser = bloodBankUserService.registerUser(user);
        return ResponseEntity.ok("User registered successfully with ID: " + registeredUser.getId());
    }


    @PostMapping("/login")
    @Operation(
            summary = "Blood Bank Login",
            description = "inputs: Email and password")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        BloodBankUser user = bloodBankUserService.login(email, password);
        Map<String, String> response = new HashMap<>();

        if (user != null) {
            String bbName = user.getBbName(); // Modify according to your model
            response.put("message", "Login successful");
            response.put("bbName", bbName); // Include the blood bank name in the response
            return ResponseEntity.ok(response); // Return a JSON object
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response); // Return a JSON object with error message
        }
    }






}
