package com.udemy.vashchykdataprojectjpa.service;

import com.udemy.vashchykdataprojectjpa.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

}
