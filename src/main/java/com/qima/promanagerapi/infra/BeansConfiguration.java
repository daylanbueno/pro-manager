package com.qima.promanagerapi.infra;


import com.qima.promanagerapi.application.ports.UserRepositoryAdapter;
import com.qima.promanagerapi.application.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public UserService userService(UserRepositoryAdapter userRepositoryAdapter) {
        return new UserService(userRepositoryAdapter);
    }

}
