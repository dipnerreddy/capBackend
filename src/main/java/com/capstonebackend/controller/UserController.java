package com.capstonebackend.controller;


import com.capstonebackend.dao.UserDAO;
import com.capstonebackend.dto.UserDTO;
import com.capstonebackend.dto.UserLastDonation;
import com.capstonebackend.dto.UserRegisterDTO;
import com.capstonebackend.dto.UserResetPassword;
import com.capstonebackend.enity.User;
import com.capstonebackend.repository.UserDTORepository;
import com.capstonebackend.repository.UserLastDonationRepo;
import com.capstonebackend.repository.UserRegisterDTORepository;
import com.capstonebackend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/userController")
@Tag(name = "User Management", description = "Operations related to users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTORepository userDTORepository;
    @Autowired
    private UserRegisterDTORepository userRegisterDTORepository;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserLastDonationRepo userLastDonationRepo;



    @PostMapping("/registration")
    @Operation(summary = "New User Registration", description = "Provide an username, phoneNumber, password and confirm password to register a new user")
    public ResponseEntity<String> newUserRegistration(@RequestBody UserRegisterDTO userRegisterDTO){
        String username = userRegisterDTO.getUserName();
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        String password = userRegisterDTO.getPassword();
        String rePassword = userRegisterDTO.getRePassword();
        String bloodType= userRegisterDTO.getBloodType();
        String address= userRegisterDTO.getAddress();
        String smallCap=address.toLowerCase();
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (!existingUser.isPresent()) {
            UserRegisterDTO newRegister = new UserRegisterDTO();
            newRegister.setUserName(username);
            newRegister.setPhoneNumber(phoneNumber);
            newRegister.setPassword(password);
            newRegister.setRePassword(rePassword);
            newRegister.setBloodType(bloodType);
            User newUser = new User(username, phoneNumber, password,bloodType,smallCap);
            userRepository.save(newUser);
            userRegisterDTORepository.save(newRegister);
            return ResponseEntity.ok("User Registration done successfully");
        }
        return ResponseEntity.status(401).body("User exists with that mobile number, please login.");
    }



    @PostMapping("/login")
    @Operation(summary = "Existing User Login", description = "Provide phoneNumber, password to Login into your user portal.")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody User user) {
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body(Map.of("message", "User does not exist with this phone number"));
        }

        User existingUser = optionalUser.get();
        if (existingUser.getPassword().equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Success");
            response.put("userName", existingUser.getUserName());  // Include the userName (or any other info)
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Wrong password, please try again"));
        }
    }



    @PostMapping("/forgot-password")
    @Operation(summary = "User Forgot Password", description = "Provide phoneNumber,otp(default otp '0000'and new-password to reset password.")
    public ResponseEntity<String> forgotPassword(@RequestBody UserResetPassword user) {
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        String otp = user.getOtp();

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body("User does not exist with this phone number");
        }

        if (!otp.equals("0000")) {
            return ResponseEntity.status(401).body("Wrong OTP");
        }

        boolean isUpdated = userDAO.resetPassword(phoneNumber, password);
        if (isUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(401).body("Unable to update password");
        }
    }


    @GetMapping("/requests/{bbName}")
    @Operation(
            summary = "Request made by the user",
            description = "It Gives the list of request that particular blood bank received")
    public ResponseEntity<List<UserDTO>> getRequestsByBloodBank(@PathVariable String bbName) {
        List<UserDTO> requests = userDTORepository.findByBbName(bbName);

        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();  // No requests found
        }

        return ResponseEntity.ok(requests);  // Return the list of requests
    }
    @PostMapping("/request-blood")
    @Operation(summary = "User Blood Request to Blood Banks", description = "Provide an username, phoneNumber, rBloodType and bbName to request a blood unit from that blood bank")
    public ResponseEntity<Map<String, String>> postUserRequst(@RequestBody UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String phoneNumber = userDTO.getPhoneNumber();
        String rBloodType = userDTO.getRBloodType();
        String bbName = userDTO.getBbName();

        System.out.println("Received blood request: " + userDTO);

        UserDTO newDTO = new UserDTO();
        newDTO.setUserName(userName);
        newDTO.setPhoneNumber(phoneNumber);
        newDTO.setRBloodType(rBloodType);
        newDTO.setBbName(bbName);

        userDTORepository.save(newDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Blood request processed successfully");
        System.out.println("Sending response: " + response);

        return ResponseEntity.ok(response);  // Ensure the response is in JSON format
    }


    @DeleteMapping("/delete-request")
    @Operation(summary = "Delete request, This is shown on the bloodBank portal", description = "")
    public ResponseEntity<String> deleteUserRequst(@RequestBody UserDTO userDTO){
        Long id=userDTO.getId();

        userDTORepository.deleteById(id);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/lastDonated/{phoneNumber}")
    public ResponseEntity<UserLastDonation> getLastDonated(@PathVariable String phoneNumber) {
        UserLastDonation user = userLastDonationRepo.findByPhoneNumber(phoneNumber);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update the last donation date
    @PostMapping("/lastDonated")
    public ResponseEntity<String> updateLastDonated(@RequestBody UserLastDonation user) {
        String phoneNumber = user.getPhoneNumber();
        String date = user.getDate();

        Optional<User> existingUser1 = userRepository.findByPhoneNumber(phoneNumber);
        UserLastDonation existingUser = userLastDonationRepo.findByPhoneNumber(phoneNumber);

        if(existingUser1 != null && existingUser ==null){
            UserLastDonation userLastDonation = new UserLastDonation(phoneNumber,date);
            userLastDonationRepo.save(userLastDonation);
            return ResponseEntity.ok("Donation date updated successfully.");
        }
        else if(existingUser1 != null && existingUser != null){
            existingUser.setDate(date);
            userLastDonationRepo.save(existingUser);
            return ResponseEntity.ok("Donation date updated successfully.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

    }

}

