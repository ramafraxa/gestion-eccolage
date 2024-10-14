package com.fresh.coding.schoolmanagementapi.controllers;

import com.fresh.coding.schoolmanagementapi.dto.ReceiptDTO;
import com.fresh.coding.schoolmanagementapi.sercices.receipts.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptDTO> getAllReceipts() {
        return receiptService.findAllReceipts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReceiptDTO saveReceipt(@RequestBody ReceiptDTO toSave) {
        return receiptService.save(toSave);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReceipt(@PathVariable Long id) {
        receiptService.delete(id);
    }
}
