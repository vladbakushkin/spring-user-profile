package org.bakushkin.springuserprofile.util;

import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;
import org.bakushkin.springuserprofile.entity.User;

public final class UserDtoMapper {

    private UserDtoMapper() {

    }

    public static UserDto toUserDto(final User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }

    public static User toUser(final NewUserDto newUserDto) {
        return User.builder()
                .lastName(newUserDto.getLastName())
                .firstName(newUserDto.getFirstName())
                .middleName(newUserDto.getMiddleName())
                .birthDate(newUserDto.getBirthDate())
                .email(newUserDto.getEmail())
                .phone(newUserDto.getPhone())
                .build();
    }
}
