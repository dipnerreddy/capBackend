package com.capstonebackend.controller;


import com.capstonebackend.dto.UserDTO;
import com.capstonebackend.enity.Bill;
import com.capstonebackend.enity.User;
import com.capstonebackend.repository.UserDTORepository;
import com.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userController")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTORepository userDTORepository;

    // New POST mapping to fetch requests based on bbName
    @GetMapping("/requests/{bbName}")
    public ResponseEntity<List<UserDTO>> getRequestsByBloodBank(@PathVariable String bbName) {
        List<UserDTO> requests = userDTORepository.findByBbName(bbName);

        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();  // No requests found
        }

        return ResponseEntity.ok(requests);  // Return the list of requests
    }
    @PostMapping("/request-blood")
    public  ResponseEntity<String> postUserRequst(@RequestBody UserDTO userDTO){
        String userName=userDTO.getUserName();
        String phoneNumber=userDTO.getPhoneNumber();
        String rBloodType=userDTO.getRBloodType();
        String bbName=userDTO.getBbName();

        UserDTO newDTO= new UserDTO();
        newDTO.setUserName(userName);
        newDTO.setPhoneNumber(phoneNumber);
        newDTO.setRBloodType(rBloodType);
        newDTO.setBbName(bbName);

        userDTORepository.save(newDTO);
        return  ResponseEntity.ok("Payment processed successfully");
    }
}

