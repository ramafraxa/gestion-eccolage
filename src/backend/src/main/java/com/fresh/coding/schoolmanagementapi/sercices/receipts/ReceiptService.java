package com.fresh.coding.schoolmanagementapi.sercices.receipts;

import com.fresh.coding.schoolmanagementapi.dto.ReceiptDTO;

import java.util.List;

public interface ReceiptService {
    List<ReceiptDTO> findAllReceipts();

    ReceiptDTO save(ReceiptDTO toSave);

    void delete(Long id);
}
