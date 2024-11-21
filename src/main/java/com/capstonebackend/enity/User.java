package com.capstonebackend.enity;


import jakarta.persistence.*;

@Entity
@Table(name = "bbUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Updated to match the correct column name in the database
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "bloodType")
    private String bloodType;

    private String address;

    public String getAddress() {
        return address;
    }

    public User(String userName, String phoneNumber, String password, String bloodType, String address) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.bloodType = bloodType;
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
