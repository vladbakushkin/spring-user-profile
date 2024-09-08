package org.bakushkin.springuserprofile.service;

import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.UpdateUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(NewUserDto newUserDto);

    UserDto getUser(Long id);

    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers(Integer from, Integer size);
}
