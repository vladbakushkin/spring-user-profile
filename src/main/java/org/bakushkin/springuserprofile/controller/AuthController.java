package org.bakushkin.springuserprofile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springuserprofile.controller.dto.JwtAuthResponseDto;
import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.SignInRequestDto;
import org.bakushkin.springuserprofile.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthResponseDto signUp(@RequestBody @Valid NewUserDto newUserDto) {
        log.info("signUp request: {}", newUserDto);
        return authService.signUp(newUserDto);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthResponseDto signIn(@RequestBody @Valid SignInRequestDto request) {
        return authService.signIn(request);
    }
}
