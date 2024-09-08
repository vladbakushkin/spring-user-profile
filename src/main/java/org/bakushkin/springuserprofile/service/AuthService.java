package org.bakushkin.springuserprofile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springuserprofile.controller.dto.JwtAuthResponseDto;
import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.SignInRequestDto;
import org.bakushkin.springuserprofile.entity.Role;
import org.bakushkin.springuserprofile.entity.User;
import org.bakushkin.springuserprofile.repository.UserRepository;
import org.bakushkin.springuserprofile.util.UserDtoMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param newUserDto данные пользователя
     * @return токен
     */
    public JwtAuthResponseDto signUp(NewUserDto newUserDto) {
        User user = UserDtoMapper.toUser(newUserDto);

        user.setUsername(newUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        log.info("registered user: {}", savedUser);

        String token = jwtService.generateToken(savedUser);

        return new JwtAuthResponseDto(token);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthResponseDto signIn(SignInRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userService.userDetailsService().loadUserByUsername(request.getUsername());

        String token = jwtService.generateToken(user);
        return new JwtAuthResponseDto(token);
    }
}
