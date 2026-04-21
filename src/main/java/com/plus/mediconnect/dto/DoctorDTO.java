package com.plus.mediconnect.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class DoctorDTO {
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
