package com.plus.mediconnect.service;

import com.plus.mediconnect.dto.AppointmentDTO;
import com.plus.mediconnect.dto.AvailabilityDTO;

import java.util.List;

public interface DoctorService {


    List<AppointmentDTO> getAllAppointmentsByDoctor(String doctorId);


    AppointmentDTO getAppointmentById(String appointmentId, String doctorId);


    AppointmentDTO updateAppointmentStatus(String appointmentId, String doctorId, String status, String doctorNotes);


    AvailabilityDTO createAvailability(String doctorId, AvailabilityDTO availabilityDTO);


    List<AvailabilityDTO> getAvailabilityByDoctor(String doctorId);
}
