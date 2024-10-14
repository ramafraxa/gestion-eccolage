package com.fresh.coding.schoolmanagementapi.dto;

import com.fresh.coding.schoolmanagementapi.emuns.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO implements Serializable {
    private UUID id;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 250, message = "Name cannot exceed 250 characters.")
    private String name;

    @NotBlank(message = "First name cannot be empty.")
    @Size(max = 250, message = "First name cannot exceed 250 characters.")
    private String firstName;

    @NotBlank(message = "Class name cannot be empty.")
    @Size(max = 120, message = "Class name cannot exceed 120 characters.")
    private String className;

    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 100, message = "Address cannot exceed 100 characters.")
    private String address;

    @NotNull(message = "Gender cannot be null.")
    private Gender gender;
}
