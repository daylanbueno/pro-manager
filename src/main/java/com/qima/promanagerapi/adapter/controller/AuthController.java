package com.qima.promanagerapi.adapter.controller;

import com.qima.promanagerapi.adapter.dtos.AuthDto;
import com.qima.promanagerapi.adapter.dtos.TokenResponseDto;
import com.qima.promanagerapi.infra.auth.AuthAdapterImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthAdapterImpl authAdapter;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto auth(@RequestBody AuthDto authDto) {

        var userAuthenticate = new UsernamePasswordAuthenticationToken(authDto.login(), authDto.password());

        authenticationManager.authenticate(userAuthenticate);

        return authAdapter.obtainToken(authDto);
    }
}
