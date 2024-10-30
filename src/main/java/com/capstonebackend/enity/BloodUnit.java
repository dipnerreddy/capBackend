package com.capstonebackend.enity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "blood_units")
public class BloodUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodType;
    private String bid;
    private String bbName;
    private int quantity;
    private LocalDate expirationDate;

    // Getters and Setters


    public BloodUnit() {
    }

    public BloodUnit(String bloodType, int quantity, LocalDate expirationDate, String bid, String bbName) {
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.bid = bid;
        this.bbName = bbName;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
