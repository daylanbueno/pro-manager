package com.qima.promanagerapi.infra.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.qima.promanagerapi.adapter.dtos.AuthDto;
import com.qima.promanagerapi.adapter.dtos.TokenResponseDto;
import com.qima.promanagerapi.adapter.repositories.UserRepository;
import com.qima.promanagerapi.application.domain.User;
import com.qima.promanagerapi.application.exceptions.UnauthorizedException;
import com.qima.promanagerapi.application.ports.UserRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class AuthAdapterImpl implements AuthAdapter {

    @Value("${auth.jwt.token.secret-key}")
    private  String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer horaExpiracaoToken = 1;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;
    @Autowired
    private UserRepository userRepository;

    public TokenResponseDto obtainToken(AuthDto authDto) {
      User user = userRepositoryAdapter.obtainByLogin(authDto.login())
              .orElseThrow(() -> new UnauthorizedException("Invalid login or password"));

      return TokenResponseDto
              .builder()
              .token(buildToken(user, horaExpiracaoToken))
              .build();
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private  String buildToken(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer(applicationName)
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error to create token!" +exception.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
