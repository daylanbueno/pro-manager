package com.qima.promanagerapi.adapter.dtos;

import com.qima.promanagerapi.application.enums.RoleEnum;

public record UserDto(
        Long id,
        String name,
        String login,
        String password,
        RoleEnum role
) {
}
