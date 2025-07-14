package com.horovyak.jpa.service;

import com.horovyak.jpa.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

}
