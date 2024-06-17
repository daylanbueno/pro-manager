package com.qima.promanagerapi.application.services;

import com.qima.promanagerapi.application.domain.User;
import com.qima.promanagerapi.application.ports.UserServiceAdapter;
import com.qima.promanagerapi.application.ports.UserRepositoryAdapter;

public class UserService implements UserServiceAdapter {

    private final UserRepositoryAdapter userRepositoryAdapter;

    public UserService(UserRepositoryAdapter userRepositoryAdapter) {
        this.userRepositoryAdapter = userRepositoryAdapter;
    }

    @Override
    public User create(User user) {
        userRepositoryAdapter.obtainByLogin(user.getLogin()).ifPresent(u -> {
            throw new IllegalArgumentException("User already exists");
        });
        return userRepositoryAdapter.create(user);
    }
}
