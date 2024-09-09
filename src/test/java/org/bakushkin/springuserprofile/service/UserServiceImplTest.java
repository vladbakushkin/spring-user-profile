package org.bakushkin.springuserprofile.service;

import jakarta.persistence.EntityNotFoundException;
import org.bakushkin.springuserprofile.controller.dto.UpdateUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;
import org.bakushkin.springuserprofile.entity.User;
import org.bakushkin.springuserprofile.repository.UserRepository;
import org.bakushkin.springuserprofile.util.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUser_whenCalled_shouldReturnUserDto() {
        // given
        User user = User.builder()
                .id(1L)
                .username("testUser")
                .email("test@test.com")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        UserDto result = userService.getUser(1L);

        // then
        assertNotNull(result);
        assertEquals(result.getId(), user.getId());
        assertEquals(result.getUsername(), user.getUsername());
        assertEquals(result.getEmail(), user.getEmail());

        verify(userRepository).findById(1L);
    }

    @Test
    void getUser_whenUserNotFound_shouldThrowException() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void updateUser_whenValidDataProvided_shouldUpdateUserAndReturnUserDto() {
        // given
        Long userId = 1L;
        User existingUser = User.builder()
                .id(userId)
                .lastName("OldLastName")
                .firstName("OldFirstName")
                .middleName("OldMiddleName")
                .birthDate(LocalDate.of(1980, 1, 1))
                .email("old@example.com")
                .phone("1234567890")
                .build();

        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .lastName("NewLastName")
                .firstName("NewFirstName")
                .email("new@example.com")
                .phone("0987654321")
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .lastName("NewLastName")
                .firstName("NewFirstName")
                .middleName("OldMiddleName")
                .birthDate(LocalDate.of(1980, 1, 1))
                .email("new@example.com")
                .phone("0987654321")
                .build();

        UserDto expectedUserDto = UserDtoMapper.toUserDto(updatedUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // when
        UserDto result = userService.updateUser(userId, updateUserDto);

        // then
        assertNotNull(result);
        assertEquals(expectedUserDto.getLastName(), result.getLastName());
        assertEquals(expectedUserDto.getFirstName(), result.getFirstName());
        assertEquals(expectedUserDto.getEmail(), result.getEmail());
        assertEquals(expectedUserDto.getPhone(), result.getPhone());
        assertEquals(existingUser.getMiddleName(), result.getMiddleName());
        assertEquals(existingUser.getBirthDate(), result.getBirthDate());

        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser_whenCalled_shouldDeleteUserAndLog() {
        // given
        Long userId = 1L;

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllUsers_whenCalled_shouldReturnUserDtoListAndLog() {
        // given
        User user1 = new User();
        User user2 = new User();
        List<User> users = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(users);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // when
        List<UserDto> result = userService.getAllUsers(0, 10);

        // then
        assertNotNull(result);
        assertEquals(users.size(), result.size());
        verify(userRepository).findAll(PageRequest.of(0, 10));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void userDetailsService_whenUsernameIsValid_shouldReturnUserDetails() {
        // given
        String username = "admin";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        UserDetailsService userDetailsService = userService.userDetailsService();
        UserDetails result = userDetailsService.loadUserByUsername(username);

        // then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository).findByUsername(username);
    }
}