package com.starbux.service;

import com.starbux.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long userId);

    public UserDto createUser(UserDto user);

    public UserDto updateUser(Long userId, UserDto user);

    public boolean deleteUser(Long userId);
}