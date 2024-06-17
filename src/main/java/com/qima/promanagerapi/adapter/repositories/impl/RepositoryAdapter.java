package com.qima.promanagerapi.adapter.repositories.impl;

import com.qima.promanagerapi.adapter.converters.UserConverter;
import com.qima.promanagerapi.adapter.entities.UserEntity;
import com.qima.promanagerapi.adapter.repositories.UserRepository;
import com.qima.promanagerapi.application.domain.User;
import com.qima.promanagerapi.application.ports.UserRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RepositoryAdapter implements UserRepositoryAdapter {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {

        var passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);

        UserEntity savedUser = userRepository.save(userConverter.toEntity(user));
        return userConverter.toDomain(savedUser);
    }

    @Override
    public Optional<User> obtainByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userConverter::toDomain);

    }
}
