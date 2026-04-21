package com.plus.mediconnect.controller;

import com.plus.mediconnect.dto.AppointmentDTO;
import com.plus.mediconnect.dto.AvailabilityDTO;
import com.plus.mediconnect.dto.ResponseDto;
import com.plus.mediconnect.repo.DoctorRepo;

import com.plus.mediconnect.service.DoctorService;
import com.plus.mediconnect.service.UserService;
import com.plus.mediconnect.utils.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDto responseDto;


    private String getCurrentDoctorId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Get user details from username
            // In a real scenario, you might need to map username to userId
            // This assumes you have a way to get the doctor ID from the current user
            throw new RuntimeException("Doctor not found");
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve current doctor ID: " + e.getMessage());
        }
    }

    // ==================== APPOINTMENT MANAGEMENT ====================

    /**
     * GET /api/doctors/appointments
     */
    @GetMapping("/appointments")
    public ResponseEntity<Object> getAllAppointments() {
        try {
            String doctorId = getCurrentDoctorId();
            List<AppointmentDTO> appointments = doctorService.getAllAppointmentsByDoctor(doctorId);

            if (appointments.isEmpty()) {
                responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDto.setMessage("No appointments found");
                responseDto.setContent(appointments);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Appointments retrieved successfully");
            responseDto.setContent(appointments);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/doctors/appointments/{id}
     */
    @GetMapping("/appointments/{id}")
    public ResponseEntity<Object> getAppointmentById(@PathVariable String id) {
        try {
            String doctorId = getCurrentDoctorId();
            AppointmentDTO appointment = doctorService.getAppointmentById(id, doctorId);

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Appointment retrieved successfully");
            responseDto.setContent(appointment);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDto.setMessage("Appointment not found");
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /api/doctors/appointments/{id}/status
     */
    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<Object> updateAppointmentStatus(
            @PathVariable String id,
            @RequestBody AppointmentDTO appointmentDTO) {
        try {
            String doctorId = getCurrentDoctorId();

            if (appointmentDTO.getStatus() == null || appointmentDTO.getStatus().isEmpty()) {
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Status is required");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            AppointmentDTO updatedAppointment = doctorService.updateAppointmentStatus(
                    id,
                    doctorId,
                    appointmentDTO.getStatus(),
                    appointmentDTO.getDoctorNotes()
            );

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Appointment status updated successfully");
            responseDto.setContent(updatedAppointment);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            responseDto.setCode(VarList.RSP_FAIL);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    AVAILABILITY MANAGEMENT

    /**
     * POST /api/doctors/availability
     */
    @PostMapping("/availability")
    public ResponseEntity<Object> createAvailability(
            @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            String doctorId = getCurrentDoctorId();

            if (availabilityDTO.getAvailableDate() == null) {
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Available date is required");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            if (availabilityDTO.getStartTime() == null || availabilityDTO.getEndTime() == null) {
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("Start time and end time are required");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            if (availabilityDTO.getEndTime().isBefore(availabilityDTO.getStartTime())) {
                responseDto.setCode(VarList.RSP_FAIL);
                responseDto.setMessage("End time must be after start time");
                return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
            }

            AvailabilityDTO createdAvailability = doctorService.createAvailability(doctorId, availabilityDTO);

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Availability created successfully");
            responseDto.setContent(createdAvailability);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/doctors/availability
     */
    @GetMapping("/availability")
    public ResponseEntity<Object> getAvailability() {
        try {
            String doctorId = getCurrentDoctorId();
            List<AvailabilityDTO> availabilities = doctorService.getAvailabilityByDoctor(doctorId);

            if (availabilities.isEmpty()) {
                responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDto.setMessage("No availability slots found");
                responseDto.setContent(availabilities);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }

            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Availability slots retrieved successfully");
            responseDto.setContent(availabilities);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode(VarList.RSP_ERROR);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

