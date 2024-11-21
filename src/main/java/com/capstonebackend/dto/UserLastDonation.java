package com.capstonebackend.dto;

import jakarta.persistence.*;

import java.util.Calendar;

@Entity
public class UserLastDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String date;

    public UserLastDonation() {
    }

    public UserLastDonation(String phoneNumber, String date) {
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
