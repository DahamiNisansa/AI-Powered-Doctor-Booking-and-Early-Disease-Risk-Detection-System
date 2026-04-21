package com.plus.mediconnect.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plus.mediconnect.entity.DoctorAvailability;

@Repository
public interface DoctorAvailabilityRepo extends JpaRepository<DoctorAvailability, String> {
    List<DoctorAvailability> findByDoctorId(String doctorId);
    List<DoctorAvailability> findByDoctorIdAndAvailableDate(String doctorId, LocalDate availableDate);
    List<DoctorAvailability> findByDoctorIdAndIsAvailableTrue(String doctorId);
}
