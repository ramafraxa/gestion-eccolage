package com.fresh.coding.schoolmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWithReceiptsDTO {
    private Long id;
    private String paymentName;
    private LocalDate paymentDate;
    private Integer price;
    private String month;
    private Integer amount;
    private List<ReceiptDTO> receipts;
}
