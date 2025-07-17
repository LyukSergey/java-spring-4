package com.example.litvynspringboot.mapper;

import com.example.litvynspringboot.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(com.example.litvynspringboot.entity.User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setBankName(user.getBank().getName());

        return userDto;
    }

}
