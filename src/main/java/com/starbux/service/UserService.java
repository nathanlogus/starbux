package com.starbux.service;

import com.starbux.dto.UserDto;
import com.starbux.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUserById(Long userId);

    public User createUser(UserDto user);

    public User updateUser(Long userId, UserDto user);

    public boolean deleteUser(Long userId);
}
