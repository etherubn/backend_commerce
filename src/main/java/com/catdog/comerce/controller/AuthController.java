package com.catdog.comerce.controller;

import com.catdog.comerce.dto.GenericResponse;
import com.catdog.comerce.dto.request.LoginDto;
import com.catdog.comerce.dto.request.RegisterDto;
import com.catdog.comerce.dto.response.JwtResponse;
import com.catdog.comerce.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final IAuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> register(@RequestBody @Valid RegisterDto registerDto) {
        return new ResponseEntity<>(new GenericResponse<>(200, authService.register(registerDto),null ), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<GenericResponse<JwtResponse>> login(@RequestBody @Valid LoginDto loginDto) throws Exception {
        return new ResponseEntity<>(new GenericResponse<>(200,"success", Arrays.asList(authService.login(loginDto))), HttpStatus.OK);
    }
}
