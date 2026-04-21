package com.plus.mediconnect.service.impl;

import com.plus.mediconnect.dto.AppointmentDTO;
import com.plus.mediconnect.dto.AvailabilityDTO;
import com.plus.mediconnect.entity.Appointment;
import com.plus.mediconnect.entity.DoctorAvailability;
import com.plus.mediconnect.repo.AppointmentRepo;
import com.plus.mediconnect.repo.DoctorAvailabilityRepo;
import com.plus.mediconnect.service.DoctorService;
import com.plus.mediconnect.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private DoctorAvailabilityRepo doctorAvailabilityRepo;

    @Autowired
    private Converter converter;

    @Override
    public List<AppointmentDTO> getAllAppointmentsByDoctor(String doctorId) {
        List<Appointment> appointments = appointmentRepo.findByDoctorId(doctorId);
        return appointments.stream().map(appointment -> converter.toAppointmentDTO(appointment)).collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(String appointmentId, String doctorId) {
        Appointment appointment = appointmentRepo.findByIdAndDoctorId(appointmentId, doctorId).orElseThrow(() -> new RuntimeException("Appointment not found for doctor"));
        return converter.toAppointmentDTO(appointment);
    }

    @Override
    public AppointmentDTO updateAppointmentStatus(String appointmentId, String doctorId, String status, String doctorNotes) {
        Appointment appointment = appointmentRepo.findByIdAndDoctorId(appointmentId, doctorId).orElseThrow(() -> new RuntimeException("Appointment not found for doctor"));

        // Validate status
        String[] validStatuses = {"ACCEPTED", "REJECTED", "COMPLETED", "CANCELLED"};
        boolean isValidStatus = false;
        for (String validStatus : validStatuses) {
            if (validStatus.equals(status)) {
                isValidStatus = true;
                break;
            }
        }

        if (!isValidStatus) {
            throw new IllegalArgumentException("Invalid status. Must be one of: ACCEPTED, REJECTED, COMPLETED, CANCELLED");
        }

        appointment.setStatus(status);
        appointment.setDoctorNotes(doctorNotes);
        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment updatedAppointment = appointmentRepo.save(appointment);
        return converter.toAppointmentDTO(updatedAppointment);
    }

    @Override
    public AvailabilityDTO createAvailability(String doctorId, AvailabilityDTO availabilityDTO) {
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorId(doctorId);
        availability.setAvailableDate(availabilityDTO.getAvailableDate());
        availability.setStartTime(availabilityDTO.getStartTime());
        availability.setEndTime(availabilityDTO.getEndTime());
        availability.setIsAvailable(true);
        availability.setCreatedAt(LocalDateTime.now());
        availability.setUpdatedAt(LocalDateTime.now());

        DoctorAvailability savedAvailability = doctorAvailabilityRepo.save(availability);
        return converter.toAvailabilityDTO(savedAvailability);
    }

    @Override
    public List<AvailabilityDTO> getAvailabilityByDoctor(String doctorId) {
        List<DoctorAvailability> availabilities = doctorAvailabilityRepo.findByDoctorIdAndIsAvailableTrue(doctorId);
        return availabilities.stream().map(availability -> converter.toAvailabilityDTO(availability)).collect(Collectors.toList());
    }
}

