package com.fresh.coding.schoolmanagementapi.entities;

import com.fresh.coding.schoolmanagementapi.emuns.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column( nullable = false)
    private String firstName;

    @Column( nullable = false)
    private String className;

    @Column( nullable = false)
    private String address;

    @Column( nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Payment> payments = new ArrayList<>();
}
