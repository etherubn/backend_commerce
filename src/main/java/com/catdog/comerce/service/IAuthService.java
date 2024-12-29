package com.catdog.comerce.service;

import com.catdog.comerce.dto.request.LoginDto;
import com.catdog.comerce.dto.request.RegisterDto;
import com.catdog.comerce.dto.response.JwtResponse;


public interface IAuthService {
    String register(RegisterDto registerDto);
    JwtResponse login(LoginDto loginDto) throws Exception;
}
