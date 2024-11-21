package com.capstonebackend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserDTO {

    private String userName;
    private String phoneNumber;
    private String rBloodType;

    private  String bbName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserDTO() {
    }

    public UserDTO(String userName, String phoneNumber, String rBloodType, String bbName) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.rBloodType = rBloodType;
        this.bbName = bbName;
    }

    public String getrBloodType() {
        return rBloodType;
    }

    public void setrBloodType(String rBloodType) {
        this.rBloodType = rBloodType;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRBloodType() {
        return rBloodType;
    }

    public void setRBloodType(String rBloodType) {
        this.rBloodType = rBloodType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
