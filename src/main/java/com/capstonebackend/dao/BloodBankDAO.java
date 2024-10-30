package com.capstonebackend.dao;

public interface BloodBankDAO {
    boolean existsByBidAndBbName(String bid, String bbName);
    boolean existsByBidAndBbNameAndBloodGroup(String bid, String bbName,String bloodGroup);

    boolean delete(String bid, String bbName);
}
