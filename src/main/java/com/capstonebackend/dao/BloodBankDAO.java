package com.capstonebackend.dao;

public interface BloodBankDAO {
    boolean existsByBidAndBbName(String bid, String bbName);
}
