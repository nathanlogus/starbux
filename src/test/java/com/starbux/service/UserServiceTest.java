package com.starbux.service;

import com.starbux.dto.UserDto;
import com.starbux.mapper.UserMapper;
import com.starbux.model.User;
import com.starbux.repository.UserRepository;
import com.starbux.service.implementation.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAllUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User("User1"));
        userList.add(new User("User2"));
        userList.add(new User("User3"));
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertEquals(userList.size(), result.size());
    }

    @Test
    public void getUserById() {
        User user = new User("User1");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.getById(any())).thenReturn(user);
        User result = userService.getUserById(1L);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void createUser() {
        User user = new User("User1");
        when(userRepository.saveAndFlush(any())).thenReturn(user);
        User result = userService.createUser(userDtoFromUser(user));
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void updateUser() {
        User user = new User("User1");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.getById(any())).thenReturn(user);
        when(userRepository.saveAndFlush(any())).thenReturn(user);
        User result = userService.updateUser(1L, userDtoFromUser(user));
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    public void deleteUser() {
        User user = new User("User1");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        boolean result = userService.deleteUser(1L);
        assertEquals(true, result);
        verify(userRepository, times(1)).deleteById(any());
    }

    public User userFromDto(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userDtoFromUser(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
}