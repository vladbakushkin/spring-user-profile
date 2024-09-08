package org.bakushkin.springuserprofile.service;

import org.bakushkin.springuserprofile.controller.dto.UpdateUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDto getUser(Long id);

    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

    void deleteUser(Long id);

    List<UserDto> getAllUsers(Integer from, Integer size);

    UserDetailsService userDetailsService();
}
