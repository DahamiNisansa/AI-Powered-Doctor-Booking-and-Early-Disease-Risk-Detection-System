package com.plus.mediconnect.utils;

import com.plus.mediconnect.dto.UserDto;
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
}
