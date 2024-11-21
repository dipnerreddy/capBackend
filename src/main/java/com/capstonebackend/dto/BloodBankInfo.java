package com.capstonebackend.dto;

public class BloodBankInfo {
    private String bbName;
    private int bloodUnits;
    private String email;
    private String address;

    public BloodBankInfo(String bbName, int bloodUnits, String email, String address) {
        this.bbName = bbName;
        this.bloodUnits = bloodUnits;
        this.email = email;
        this.address = address;
    }


    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public int getBloodUnits() {
        return bloodUnits;
    }

    public void setBloodUnits(int bloodUnits) {
        this.bloodUnits = bloodUnits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
