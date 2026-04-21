package com.plus.mediconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
@Entity(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;              // PK
    private String userId;          // FK → User.id
    private String specialization;
    private String qualification;
    private int experienceYears;
    private String licenseNumber;
    private Long hospitalId;        // FK
    private String approvalStatus;  // PENDING / APPROVED / REJECTED
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
