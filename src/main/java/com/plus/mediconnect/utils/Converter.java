package com.plus.mediconnect.utils;

import com.plus.mediconnect.dto.AppointmentDTO;
import com.plus.mediconnect.dto.AvailabilityDTO;
import com.plus.mediconnect.dto.UserDto;
import com.plus.mediconnect.entity.Appointment;
import com.plus.mediconnect.entity.DoctorAvailability;
import com.plus.mediconnect.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    @Autowired
    private ModelMapper modelMapper;

    public User userDtoToEntity(UserDto userDto) {
        return modelMapper.map (userDto, User.class);
    }

    public AppointmentDTO toAppointmentDTO(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public Appointment toAppointmentEntity(AppointmentDTO appointmentDTO) {
        return modelMapper.map(appointmentDTO, Appointment.class);
    }

    public AvailabilityDTO toAvailabilityDTO(DoctorAvailability availability) {
        return modelMapper.map(availability, AvailabilityDTO.class);
    }

    public DoctorAvailability toAvailabilityEntity(AvailabilityDTO availabilityDTO) {
        return modelMapper.map(availabilityDTO, DoctorAvailability.class);
    }
}

