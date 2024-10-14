package com.fresh.coding.schoolmanagementapi.dto;

import com.fresh.coding.schoolmanagementapi.emuns.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithPaymentsDTO {
    private UUID id;
    private String name;
    private String firstName;
    private String className;
    private String address;
    private Gender gender;
    private List<PaymentDTO> payments;
}
