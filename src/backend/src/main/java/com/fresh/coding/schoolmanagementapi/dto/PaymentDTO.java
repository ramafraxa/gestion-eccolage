package com.fresh.coding.schoolmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {
    private Long id;

    private UUID studentId;

    private String paymentName;

    private LocalDate paymentDate;

    private Integer price;

    private String month;

    private Integer amount;
}
