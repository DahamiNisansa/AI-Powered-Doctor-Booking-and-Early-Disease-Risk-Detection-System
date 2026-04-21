package com.plus.mediconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Data
@Entity(name ="Appointment_details")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;               // PK
    private String doctorId;         // FK
    private String patientId;        // FK

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;           // PENDING / ACCEPTED / REJECTED / COMPLETED / CANCELLED
    private String reason;
    private String doctorNotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
