package com.qima.promanagerapi.application.ports;

import com.qima.promanagerapi.application.domain.User;

import java.util.Optional;

public interface UserRepositoryAdapter {

    User create(User user);

    Optional<User> obtainByLogin(String login);

}
