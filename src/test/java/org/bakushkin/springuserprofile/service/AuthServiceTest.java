package org.bakushkin.springuserprofile.service;

import org.bakushkin.springuserprofile.controller.dto.JwtAuthResponseDto;
import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.SignInRequestDto;
import org.bakushkin.springuserprofile.entity.User;
import org.bakushkin.springuserprofile.repository.UserRepository;
import org.bakushkin.springuserprofile.util.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void signUp_whenNewUserDtoIsValid_shouldReturnJwtAuthResponseDto() {
        // given
        NewUserDto newUserDto = NewUserDto.builder()
                .username("admin")
                .password("admin")
                .build();

        User user = UserDtoMapper.toUser(newUserDto);
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));

        String token = "dummy-jwt-token";
        JwtAuthResponseDto expectedResponse = new JwtAuthResponseDto(token);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(token);

        // when
        JwtAuthResponseDto result = authService.signUp(newUserDto);

        // then
        assertNotNull(result);
        assertEquals(expectedResponse.getToken(), result.getToken());

        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }


    @Test
    void signIn_whenRequestIsValid_shouldReturnJwtAuthResponseDto() {
        // given
        SignInRequestDto request = SignInRequestDto.builder()
                .username("admin")
                .password("admin")
                .build();

        UserDetails userDetails = mock(UserDetails.class);
        String token = "dummy-jwt-token";
        JwtAuthResponseDto expectedResponse = new JwtAuthResponseDto(token);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(userService.userDetailsService()).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername(request.getUsername()))
                .thenReturn(userDetails);

        when(jwtService.generateToken(userDetails)).thenReturn(token);

        // when
        JwtAuthResponseDto result = authService.signIn(request);

        // then
        assertNotNull(result);
        assertEquals(expectedResponse.getToken(), result.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(request.getUsername());
        verify(jwtService).generateToken(userDetails);
    }
}