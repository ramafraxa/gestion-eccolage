package com.fresh.coding.schoolmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO implements Serializable {
    private Long id;
    private Long paymentId;
    private LocalDate issueDate;
    private String receiptNumber;
    private String description;
}
