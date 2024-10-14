package com.fresh.coding.schoolmanagementapi.sercices.payments;

import com.fresh.coding.schoolmanagementapi.dto.*;
import com.fresh.coding.schoolmanagementapi.entities.Payment;
import com.fresh.coding.schoolmanagementapi.entities.Receipt;
import com.fresh.coding.schoolmanagementapi.entities.Student;
import com.fresh.coding.schoolmanagementapi.exceptions.HttpNotFoundException;
import com.fresh.coding.schoolmanagementapi.repositories.PaymentRepository;
import com.fresh.coding.schoolmanagementapi.repositories.ReceiptRepository;
import com.fresh.coding.schoolmanagementapi.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final ReceiptRepository receiptRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<PaymentDTO> findAllPayments() {
        log.info("Fetching all payments...");
        List<PaymentDTO> payments = paymentRepository.findAll()
                .stream()
                .map(this::toPaymentDTO)
                .collect(Collectors.toList());
        log.info("Found {} payments", payments.size());
        return payments;
    }

    @Override
    public PaymentDTO save(PaymentDTO toSave) {
        log.info("Saving payment for student with ID: {}", toSave.getStudentId());
        var payment = toSave.getId() != null ?
                paymentRepository.findById(toSave.getId())
                        .orElse(new Payment())
                : new Payment();

        if (toSave.getStudentId() != null) {
            log.debug("Looking for student with ID: {}", toSave.getStudentId());
            Student student = studentRepository.findById(toSave.getStudentId())
                    .orElseThrow(() -> {
                        log.error("Student not found with ID: {}", toSave.getStudentId());
                        return new HttpNotFoundException("Student not found with id: " + toSave.getStudentId());
                    });
            payment.setStudent(student);
        }

        BeanUtils.copyProperties(toSave, payment, "student", "receipts");
        payment = paymentRepository.save(payment);
        log.info("Payment saved successfully with ID: {}", payment.getId());

        return toPaymentDTO(payment);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting payment with ID: {}", id);
        if (!paymentRepository.existsById(id)) {
            log.error("Payment not found with ID: {}", id);
            throw new HttpNotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
        log.info("Payment deleted successfully with ID: {}", id);
    }

    @Override
    public StudentWithPaymentsDTO findStudentWithPayments(UUID studentId) {
        log.info("Fetching payments for student with ID: {}", studentId);
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new HttpNotFoundException("Student not found with id: " + studentId));

        var payments = paymentRepository.findAllByStudentId(studentId);
        var paymentDTOs = payments.stream()
                .map(this::toPaymentDTO)
                .collect(Collectors.toList());

        var studentWithPaymentsDTO = new StudentWithPaymentsDTO();
        BeanUtils.copyProperties(student, studentWithPaymentsDTO);
        studentWithPaymentsDTO.setPayments(paymentDTOs);

        log.info("Found {} payments for student with ID: {}", paymentDTOs.size(), studentId);
        return studentWithPaymentsDTO;
    }

    @Override
    public PaymentWithReceiptsDTO findPaymentWithReceipts(Long paymentId) {
        log.info("Fetching payments with ID: {}", paymentId);
        var payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found with id: " + paymentId));

        var paymentDTO = new PaymentWithReceiptsDTO();
        BeanUtils.copyProperties(payment, paymentDTO);

        var receiptDTOs = receiptRepository.findAllByPaymentId(paymentId).stream()
                .map(this::toReceiptDTO)
                .collect(Collectors.toList());

        paymentDTO.setReceipts(receiptDTOs);
        log.info("Found {} receipts for payment with ID: {}", receiptDTOs.size(), paymentId);

        return paymentDTO;
    }

    private PaymentDTO toPaymentDTO(Payment payment) {
        log.debug("Converting payment entity to DTO for payment ID: {}", payment.getId());
        PaymentDTO paymentDTO = new PaymentDTO();
        BeanUtils.copyProperties(payment, paymentDTO, "receipts");

        if (payment.getStudent() != null) {
            paymentDTO.setStudentId(payment.getStudent().getId());
        }

        return paymentDTO;
    }

    private ReceiptDTO toReceiptDTO(Receipt receipt) {
        log.debug("Converting receipt entity to DTO for receipt ID: {}", receipt.getId());
        ReceiptDTO receiptDTO = new ReceiptDTO();
        BeanUtils.copyProperties(receipt, receiptDTO);
        receiptDTO.setPaymentId(receipt.getPayment().getId());
        return receiptDTO;
    }


    @Override
    public StudentPaymentReceiptsDTO findStudentPaymentReceipts(UUID studentId) {
        log.info("Fetching payment receipts for student with ID: {}", studentId);

        var student = findStudentById(studentId);
         var paymentDTOs = findPaymentsWithReceipts(studentId);

        return createStudentPaymentReceiptsDTO(student, paymentDTOs);
    }

    @Override
    public PaymentDTO findPaymentById(Long id) {
        log.info("Fetching payment with ID: {}", id);
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found with id: " + id));
        return toPaymentDTO(payment);
    }

    private Student findStudentById(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("Students not found with ID: {}", studentId);
                    return new HttpNotFoundException("Student not found with id: " + studentId);
                });
    }

    private List<PaymentWithReceiptsDTO> findPaymentsWithReceipts(UUID studentId) {
        var payments = paymentRepository.findAllByStudentId(studentId);
        log.info("Found {} payments for student ID: {}", payments.size(), studentId);

        return payments.stream()
                .map(this::mapPaymentToDTO)
                .collect(Collectors.toList());
    }

    private PaymentWithReceiptsDTO mapPaymentToDTO(Payment payment) {
        log.debug("Mapping payment ID: {}", payment.getId());

        var receipts = receiptRepository.findAllByPaymentId(payment.getId()).stream()
                .map(this::mapReceiptToDTO)
                .collect(Collectors.toList());

        log.debug("Found {} receipts for payment ID: {}", receipts.size(), payment.getId());

        return new PaymentWithReceiptsDTO(
                payment.getId(),
                payment.getPaymentName(),
                payment.getPaymentDate(),
                payment.getPrice(),
                payment.getMonth(),
                payment.getAmount(),
                receipts
        );
    }

    private ReceiptDTO mapReceiptToDTO(Receipt receipt) {
        return new ReceiptDTO(
                receipt.getId(),
                receipt.getPayment().getId(),
                receipt.getIssueDate(),
                receipt.getReceiptNumber(),
                receipt.getDescription()
        );
    }

    private StudentPaymentReceiptsDTO createStudentPaymentReceiptsDTO(Student student, List<PaymentWithReceiptsDTO> paymentDTOs) {
        return new StudentPaymentReceiptsDTO(
                student.getId(),
                student.getName(),
                student.getFirstName(),
                student.getClassName(),
                student.getAddress(),
                student.getGender(),
                paymentDTOs
        );
    }

}
