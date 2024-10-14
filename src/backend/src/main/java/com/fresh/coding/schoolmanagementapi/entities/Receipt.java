package com.fresh.coding.schoolmanagementapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private String receiptNumber;

    @Column(columnDefinition = "TEXT")
    private String description;
}
