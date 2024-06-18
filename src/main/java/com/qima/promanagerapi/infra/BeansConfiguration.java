package com.qima.promanagerapi.infra;


import com.qima.promanagerapi.application.ports.CategoryRepositoryAdapter;
import com.qima.promanagerapi.application.ports.ProductRepositoryAdapter;
import com.qima.promanagerapi.application.ports.UserRepositoryAdapter;
import com.qima.promanagerapi.application.services.CategoryService;
import com.qima.promanagerapi.application.services.ProductService;
import com.qima.promanagerapi.application.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfiguration {

    @Bean
    public UserService userService(UserRepositoryAdapter userRepositoryAdapter) {
        return new UserService(userRepositoryAdapter);
    }

    @Bean
    public CategoryService categoryService(CategoryRepositoryAdapter categoryRepositoryAdapter) {
        return new CategoryService(categoryRepositoryAdapter);
    }

    @Bean
    public ProductService productService(ProductRepositoryAdapter productRepositoryAdapter) {
        return new ProductService(productRepositoryAdapter);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
