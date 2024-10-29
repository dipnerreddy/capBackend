package com.capstonebackend.repository;

import com.capstonebackend.enity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
