package com.qima.promanagerapi.adapter.controller;

import com.qima.promanagerapi.adapter.converters.UserConverter;
import com.qima.promanagerapi.adapter.dtos.UserDto;
import com.qima.promanagerapi.application.domain.User;
import com.qima.promanagerapi.application.ports.UserServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceAdapter userServiceAdapter;
    private final UserConverter userConverter;

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userServiceAdapter.create(userConverter.toDomain(userDto));
        return userConverter.toUserDto(user);
    }

}
