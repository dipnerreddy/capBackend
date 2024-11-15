package com.capstonebackend.service;

import com.capstonebackend.dto.BloodBankInfo;
import com.capstonebackend.repository.BloodBankUserRepository;
import com.capstonebackend.repository.BloodUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodBankService {

    @Autowired
    private BloodBankUserRepository userRepository;

    @Autowired
    private BloodUnitRepository unitRepository;

    public List<BloodBankInfo> findBloodBanks(String address, String bloodType) {
        List<String> bloodBanks = unitRepository.findBbNamesByBloodType(bloodType);
        List<BloodBankInfo> result = new ArrayList<>();

        for (String bbName : bloodBanks) {
            userRepository.findByBbName(bbName).ifPresent(user -> {
                if (user.getAddress().contains(address)) {
                    int unitCount = unitRepository.countByBbNameAndBloodType(bbName, bloodType);
                    result.add(new BloodBankInfo(user.getBbName(), unitCount, user.getEmail(), user.getAddress()));
                }
            });
        }

        return result;
    }
}
