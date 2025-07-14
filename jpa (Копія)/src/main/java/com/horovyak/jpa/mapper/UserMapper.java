package com.horovyak.jpa.mapper;

import com.horovyak.jpa.dto.UserDto;
import com.horovyak.jpa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
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
