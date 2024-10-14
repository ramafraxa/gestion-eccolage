package com.fresh.coding.schoolmanagementapi.repositories;

import com.fresh.coding.schoolmanagementapi.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudentId(UUID studentId);
}
