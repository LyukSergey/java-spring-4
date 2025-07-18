package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(com.example.demo.entity.User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getSurname(), user.getBank().getName());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setBankName(user.getBank().getName());

        return userDto;
    }

}
