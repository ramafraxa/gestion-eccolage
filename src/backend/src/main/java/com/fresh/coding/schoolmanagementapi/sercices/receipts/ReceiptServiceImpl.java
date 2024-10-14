package com.fresh.coding.schoolmanagementapi.sercices.receipts;

import com.fresh.coding.schoolmanagementapi.dto.ReceiptDTO;
import com.fresh.coding.schoolmanagementapi.entities.Payment;
import com.fresh.coding.schoolmanagementapi.entities.Receipt;
import com.fresh.coding.schoolmanagementapi.exceptions.HttpNotFoundException;
import com.fresh.coding.schoolmanagementapi.repositories.PaymentRepository;
import com.fresh.coding.schoolmanagementapi.repositories.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public List<ReceiptDTO> findAllReceipts() {
        log.info("Fetching all receipts...");
        var receipts = receiptRepository.findAll()
                .stream()
                .map(this::toReceiptDTO)
                .collect(Collectors.toList());
        log.info("Found {} receipts", receipts.size());
        return receipts;
    }

    @Transactional
    @Override
    public ReceiptDTO save(ReceiptDTO toSave) {
        log.info("Saving receipt with ID: {}", toSave.getId() != null ? toSave.getId() : "New receipt");

        var receipt = toSave.getId() != null ?
                receiptRepository.findById(toSave.getId())
                        .orElse(new Receipt())
                : new Receipt();

        if (toSave.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(toSave.getPaymentId())
                    .orElseThrow(() -> new HttpNotFoundException("Payment not found with id: " + toSave.getPaymentId()));
            receipt.setPayment(payment);
        }

        BeanUtils.copyProperties(toSave, receipt, "payment");

        receipt = receiptRepository.save(receipt);
        log.info("Receipt saved successfully with ID: {}", receipt.getId());

        return toReceiptDTO(receipt);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting receipt with ID: {}", id);
        if (!receiptRepository.existsById(id)) {
            log.error("Receipt not found with ID: {}", id);
            throw new HttpNotFoundException("Receipt not found with id: " + id);
        }
        receiptRepository.deleteById(id);
        log.info("Receipt deleted successfully with ID: {}", id);
    }

    private ReceiptDTO toReceiptDTO(Receipt receipt) {
        var receiptDTO = new ReceiptDTO();
        BeanUtils.copyProperties(receipt, receiptDTO);
        receiptDTO.setPaymentId(receipt.getPayment().getId());
        return receiptDTO;
    }
}
