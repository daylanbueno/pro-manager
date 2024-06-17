package com.qima.promanagerapi.infra.security;

import com.qima.promanagerapi.adapter.entities.UserEntity;
import com.qima.promanagerapi.adapter.repositories.UserRepository;
import com.qima.promanagerapi.application.exceptions.UnauthorizedException;
import com.qima.promanagerapi.infra.auth.AuthAdapterImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AuthAdapterImpl authAdapter;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractHeader(request);

        if (token != null) {
            String login = authAdapter.validateToken(token);
            UserEntity user = userRepository.findByLogin(login).orElse(null);

            if (user == null) {
                throw  new UnauthorizedException("Unauthorizad");
            }

            var autentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autentication);
        }

        filterChain.doFilter(request, response);
    }

    public String extractHeader(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return null;
        }

        if (!authHeader.split(" ")[0].equals("Bearer")) {
            return  null;
        }

        return authHeader.split(" ")[1];
    }
}
