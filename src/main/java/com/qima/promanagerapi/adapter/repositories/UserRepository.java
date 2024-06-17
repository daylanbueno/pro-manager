package com.qima.promanagerapi.adapter.repositories;

import com.qima.promanagerapi.adapter.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional <UserEntity> findByLogin(String login);
}
