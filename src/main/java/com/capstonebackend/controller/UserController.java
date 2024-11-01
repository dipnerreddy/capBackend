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
        String username=userRegisterDTO.getUserName();
        String phoneNumber=userRegisterDTO.getPhoneNumber();
        String password=userRegisterDTO.getPassword();
        String rePassword=userRegisterDTO.getRePassword();

        boolean val=userRepository.findByPhoneNumber(phoneNumber);
        if(!val){
            UserRegisterDTO newRegister = new UserRegisterDTO();
            newRegister.setUserName(username);
            newRegister.setPhoneNumber(phoneNumber);
            newRegister.setPassword(password);
            newRegister.setRePassword(rePassword);

            User newUser=new User(username,phoneNumber,password);
            userRepository.save(newUser);
            userRegisterDTORepository.save(newRegister);
            return  ResponseEntity.ok("User Registration done successfully");
        }
        return ResponseEntity.status(401).body("User Exists with that mobile number, please login.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User user){
        String phoneNumber=user.getPhoneNumber();
        String password=user.getPassword();

        boolean flag = userRepository.findByPhoneNumber(phoneNumber);

        if(!flag){
            boolean pass =userRepository.findByPassword(password);
            if(pass){
                return ResponseEntity.ok("Login Success");
            }
            return ResponseEntity.status(401).body("Wrong Password, re try");
        }
        return ResponseEntity.status(401).body("User does not Exists with this phone number");

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserResetPassword user){
        String phoneNumber=user.getPhoneNumber();
        String password=user.getPassword();
        String otp=user.getOtp();
        boolean flag = userRepository.findByPhoneNumber(phoneNumber);
        if(flag){
            if(otp.equals("0000")){
                boolean T=userDAO.resetPassword(phoneNumber,password);
                if(T){
                    return ResponseEntity.ok("Updated");
                }
                return ResponseEntity.status(401).body("unable to update");
            }
            return ResponseEntity.status(401).body("Wrong OTP");
        }
        return ResponseEntity.status(401).body("user does not exits.");
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

