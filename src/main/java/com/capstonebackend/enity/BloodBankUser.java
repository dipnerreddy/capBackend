package com.capstonebackend.enity;

import jakarta.persistence.*;

@Entity
@Table(name = "blood_bank_user")
public class BloodBankUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bbName;
    private String email;
    private String password;
    private String address;

    public BloodBankUser() {
    }

    public BloodBankUser(Long id, String bbName, String email, String password, String address) {
        this.id = id;
        this.bbName = bbName;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    // g&S

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
