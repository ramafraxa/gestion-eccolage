package com.fresh.coding.schoolmanagementapi.controllers;

import com.fresh.coding.schoolmanagementapi.dto.PaymentDTO;
import com.fresh.coding.schoolmanagementapi.dto.PaymentWithReceiptsDTO;
import com.fresh.coding.schoolmanagementapi.dto.StudentPaymentReceiptsDTO;
import com.fresh.coding.schoolmanagementapi.dto.StudentWithPaymentsDTO;
import com.fresh.coding.schoolmanagementapi.sercices.payments.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDTO> getAllPayments(){
        return paymentService.findAllPayments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDTO getPayment(@PathVariable Long id){
        return paymentService.findPaymentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentDTO savePayment(@RequestBody PaymentDTO toSave){
        return paymentService.save(toSave);
    }


    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentWithPaymentsDTO getPaymentsByStudentId(@PathVariable UUID studentId) {
        return paymentService.findStudentWithPayments(studentId);
    }

    @GetMapping("/{paymentId}/receipts")
    public PaymentWithReceiptsDTO findPaymentWithReceipts(@PathVariable Long paymentId) {
       return paymentService.findPaymentWithReceipts(paymentId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        paymentService.delete(id);
    }


    @GetMapping("/students/{studentId}/receipts")
    public StudentPaymentReceiptsDTO findStudentPaymentReceipts(@PathVariable UUID studentId) {
       return paymentService.findStudentPaymentReceipts(studentId);
    }
}
