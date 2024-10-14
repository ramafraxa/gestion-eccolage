package com.fresh.coding.schoolmanagementapi.repositories;

import com.fresh.coding.schoolmanagementapi.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByPaymentId(Long paymentId);
}
