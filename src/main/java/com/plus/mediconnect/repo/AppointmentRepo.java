package com.plus.mediconnect.repo;

import com.plus.mediconnect.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, String> {
    List<Appointment> findByDoctorId(String doctorId);
    Optional<Appointment> findByIdAndDoctorId(String id, String doctorId);
}
