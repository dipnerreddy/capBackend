package com.capstonebackend.enity;

import jakarta.persistence.*;


@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String patientNumber;
    private String amount;
    private String bid;
    private String bloodType;
    private String bbName;

    public Bill() {
    }

    public Bill(String patientName, String patientNumber, String amount, String bid, String bloodType, String bbName) {
        this.patientName = patientName;
        this.patientNumber = patientNumber;
        this.amount = amount;
        this.bid = bid;
        this.bloodType = bloodType;
        this.bbName = bbName;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
