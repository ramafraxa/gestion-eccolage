package com.fresh.coding.schoolmanagementapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Student student;

    @Column( nullable = false)
    private String paymentName;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column( nullable = false)
    private Integer price;

    @Column( nullable = false)
    private String month;

    @Column( nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Receipt> receipts = new ArrayList<>();
}
