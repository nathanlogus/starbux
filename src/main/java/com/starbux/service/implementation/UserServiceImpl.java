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

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.getById(userId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested user was not found!");
    }

    @Override
    public User createUser(UserDto userDto) {
        log.info("Creating user: {}", userDto);
        return userRepository.saveAndFlush(userMapper.userFromDto(userDto));
    }

    @Override
    public User updateUser(Long userId, UserDto userDto) {
        if (userRepository.findById(userId).isPresent()) {
            log.info("Updating user: {}", userDto);
            User userObject = userRepository.getById(userId);
            userObject.setName(userDto.getName());
            return userRepository.saveAndFlush(userObject);
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
