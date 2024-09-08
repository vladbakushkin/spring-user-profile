package org.bakushkin.springuserprofile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bakushkin.springuserprofile.controller.dto.NewUserDto;
import org.bakushkin.springuserprofile.controller.dto.UpdateUserDto;
import org.bakushkin.springuserprofile.controller.dto.UserDto;
import org.bakushkin.springuserprofile.entity.User;
import org.bakushkin.springuserprofile.repository.UserRepository;
import org.bakushkin.springuserprofile.util.UserDtoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto registerUser(NewUserDto newUserDto) {
        User user = UserDtoMapper.toUser(newUserDto);
        User savedUser = userRepository.save(user);
        log.info("created user: {}", savedUser);
        return UserDtoMapper.toUserDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = findUserById(id);
        log.info("got user: {}", user);
        return UserDtoMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User userToSave = findUserById(id);

        if (updateUserDto.getLastName() != null) {
            userToSave.setLastName(updateUserDto.getLastName());
        }

        if (updateUserDto.getFirstName() != null) {
            userToSave.setFirstName(updateUserDto.getFirstName());
        }

        if (updateUserDto.getMiddleName() != null) {
            userToSave.setMiddleName(updateUserDto.getMiddleName());
        }

        if (updateUserDto.getBirthDate() != null) {
            userToSave.setBirthDate(updateUserDto.getBirthDate());
        }

        if (updateUserDto.getEmail() != null) {
            userToSave.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getPhone() != null) {
            userToSave.setPhone(updateUserDto.getPhone());
        }

        User updatedUser = userRepository.save(userToSave);

        log.info("updated user: {}", updatedUser);

        return UserDtoMapper.toUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("deleted user: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers(Integer from, Integer size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<User> users = userRepository.findAll(pageable).getContent();

        log.info("found users: {}", users.size());

        return users.stream()
                .map(UserDtoMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found"));
    }
}
