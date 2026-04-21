package com.plus.mediconnect.repo;

import com.plus.mediconnect.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, String> {
    Optional<Doctor> findByUserId(String userId);
}

