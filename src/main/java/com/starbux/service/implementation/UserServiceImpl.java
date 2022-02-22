package com.starbux.service.implementation;

import com.starbux.dto.UserDto;
import com.starbux.mapper.UserMapper;
import com.starbux.model.User;
import com.starbux.repository.UserRepository;
import com.starbux.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> userMapper.userDtoFromUser(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        if (userRepository.findById(userId).isPresent())
            return userMapper.userDtoFromUser(userRepository.findById(userId).get());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found!");
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user: {}", userDto);
        User userObject = userMapper.userFromDto(userDto);
        return userMapper.userDtoFromUser(userRepository.saveAndFlush(userObject));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        if (userRepository.findById(userId).isPresent()) {
            log.info("Updating user: {}", userDto);
            User userObject = userRepository.findById(userId).get();
            userObject.setName(userDto.getName());
            return userMapper.userDtoFromUser(userRepository.saveAndFlush(userObject));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found!");
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            log.info("Deleting user with Id: {}", userId);
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
