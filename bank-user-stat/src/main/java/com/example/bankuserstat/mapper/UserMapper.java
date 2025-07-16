package com.example.bankuserstat.mapper;

import com.example.bankuserstat.entity.User;
import com.example.bankuserstat.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setBankId(user.getBank() != null ? user.getBank().getId() : null);
        return dto;
    }
}
