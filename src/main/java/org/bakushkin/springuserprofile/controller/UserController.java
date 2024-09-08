package org.bakushkin.springuserprofile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springuserprofile.controller.dto.UpdateUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;
import org.bakushkin.springuserprofile.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "API для пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        log.info("Getting user by id: {}", id);
        return userService.getUser(id);
    }

    @Operation(summary = "Обновление данных пользователя по id")
    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody @Valid UpdateUserDto updateUserDto) {
        log.info("Updating user: {}", updateUserDto);
        return userService.updateUser(id, updateUserDto);
    }

    @Operation(summary = "Удаление пользователя по id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.info("Deleting user by id: {}", id);
        userService.deleteUser(id);
    }

    @Operation(summary = "Получение всех пользователей")
    @GetMapping
    public List<UserDto> getAllUsers(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Getting all users with pagination: from {}, size {}", from, size);
        return userService.getAllUsers(from, size);
    }
}
