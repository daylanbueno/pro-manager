package com.qima.promanagerapi.infra.auth;

import com.qima.promanagerapi.adapter.dtos.AuthDto;
import com.qima.promanagerapi.adapter.dtos.TokenResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthAdapter extends UserDetailsService {

    public TokenResponseDto obtainToken(AuthDto authDto);

    public String validateToken(String token);

}
