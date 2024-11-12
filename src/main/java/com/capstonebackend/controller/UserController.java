package com.capstonebackend.controller;


import com.capstonebackend.dao.UserDAO;
import com.capstonebackend.dto.UserDTO;
import com.capstonebackend.dto.UserRegisterDTO;
import com.capstonebackend.dto.UserResetPassword;
import com.capstonebackend.enity.Bill;
import com.capstonebackend.enity.User;
import com.capstonebackend.repository.UserDTORepository;
import com.capstonebackend.repository.UserRegisterDTORepository;
import com.capstonebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userController")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTORepository userDTORepository;
    @Autowired
    private UserRegisterDTORepository userRegisterDTORepository;
    @Autowired
    private UserDAO userDAO;



    @PostMapping("/registration")
    public ResponseEntity<String> newUserRegistration(@RequestBody UserRegisterDTO userRegisterDTO){
        String username = userRegisterDTO.getUserName();
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        String password = userRegisterDTO.getPassword();
        String rePassword = userRegisterDTO.getRePassword();

        // Check if the user with the given phone number exists
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);

        // If the user does not exist, proceed with registration
        if (!existingUser.isPresent()) {
            UserRegisterDTO newRegister = new UserRegisterDTO();
            newRegister.setUserName(username);
            newRegister.setPhoneNumber(phoneNumber);
            newRegister.setPassword(password);
            newRegister.setRePassword(rePassword);

            // Save the new user in the database
            User newUser = new User(username, phoneNumber, password);
            userRepository.save(newUser);
            userRegisterDTORepository.save(newRegister);

            return ResponseEntity.ok("User Registration done successfully");
        }

        // If the user already exists, return an error message
        return ResponseEntity.status(401).body("User exists with that mobile number, please login.");
    }



    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User user) {
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();

        // Find user by phone number
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        // If the user does not exist
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body("User does not exist with this phone number");
        }

        // Check if the password matches
        User existingUser = optionalUser.get();
        if (existingUser.getPassword().equals(password)) {
            return ResponseEntity.ok("Login Success");
        } else {
            return ResponseEntity.status(401).body("Wrong password, please try again");
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserResetPassword user) {
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        String otp = user.getOtp();

        // Find user by phone number
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        // If the user does not exist
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body("User does not exist with this phone number");
        }

        // Check if the OTP is correct (for simplicity, assuming the OTP is hardcoded as "0000")
        if (!otp.equals("0000")) {
            return ResponseEntity.status(401).body("Wrong OTP");
        }

        // Proceed to reset the password
        boolean isUpdated = userDAO.resetPassword(phoneNumber, password);
        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(401).body("Unable to update password");
        }
    }


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

    @DeleteMapping("/delete-request")
    public ResponseEntity<String> deleteUserRequst(@RequestBody UserDTO userDTO){
        Long id=userDTO.getId();

        userDTORepository.deleteById(id);

        return ResponseEntity.ok("Deleted successfully");
    }
}

