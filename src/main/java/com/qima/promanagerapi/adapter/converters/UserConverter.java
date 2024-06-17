package com.qima.promanagerapi.adapter.converters;

import com.qima.promanagerapi.adapter.dtos.UserDto;
import com.qima.promanagerapi.adapter.entities.UserEntity;
import com.qima.promanagerapi.application.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                user.getRole() );
    }

    public User toDomain(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.login(),
                userDto.password(),
                userDto.role()) ;
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getPassword(),
                user.getRole() );
    }
    public  User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getLogin(),
                userEntity.getPassword(),
                userEntity.getRole() );
    }
}
