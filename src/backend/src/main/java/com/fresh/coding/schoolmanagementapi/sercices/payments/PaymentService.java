package com.fresh.coding.schoolmanagementapi.sercices.payments;

import com.fresh.coding.schoolmanagementapi.dto.*;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<PaymentDTO> findAllPayments();

    PaymentDTO save(PaymentDTO toSave);

    void delete(Long id);

    StudentWithPaymentsDTO findStudentWithPayments(UUID studentId);

    PaymentWithReceiptsDTO findPaymentWithReceipts(Long paymentId);

    StudentPaymentReceiptsDTO findStudentPaymentReceipts(UUID studentId);

    PaymentDTO findPaymentById(Long id);
}
