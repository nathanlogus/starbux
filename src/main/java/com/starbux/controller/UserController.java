package com.starbux.controller;

import com.starbux.dto.UserDto;
import com.starbux.mapper.UserMapper;
import com.starbux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(user -> userMapper.userDtoFromUser(user)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.userDtoFromUser(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return new ResponseEntity(userMapper.userDtoFromUser(userService.createUser(user)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
        return ResponseEntity.ok(userMapper.userDtoFromUser(userService.updateUser(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        boolean isRemoved = userService.deleteUser(id);
        if (isRemoved) {
            return new ResponseEntity("Succesfully removed user!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
