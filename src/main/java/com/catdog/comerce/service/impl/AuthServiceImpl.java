package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.LoginDto;
import com.catdog.comerce.dto.request.RegisterDto;
import com.catdog.comerce.dto.response.JwtResponse;
import com.catdog.comerce.entity.Role;
import com.catdog.comerce.entity.User;
import com.catdog.comerce.enums.RoleType;
import com.catdog.comerce.exception.AlreadyExistsException;
import com.catdog.comerce.exception.NotFoundException;
import com.catdog.comerce.repository.RoleRepo;
import com.catdog.comerce.repository.UserRepo;
import com.catdog.comerce.security.utils.JwtTokenUtils;
import com.catdog.comerce.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    @Transactional
    public String register(RegisterDto registerDto) {
        Map<String,Object> errors = validateRegisterData(registerDto);

        if (!errors.isEmpty()){
            throw new AlreadyExistsException("user", errors.toString());
        }
        User user = new User();
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setEmail(registerDto.email());
        Role role = roleRepo.findByType(RoleType.USER)
                        .orElseThrow(()-> new NotFoundException("role",0L));
        user.setRole(Set.of(role));

        userRepo.save(user);

        return "User register successful";
    }

    private Map<String,Object> validateRegisterData(RegisterDto registerDto) {
        Map<String,Object> error = new HashMap<>();
        if (userRepo.existsByUsername(registerDto.username())) {
            error.put("username",registerDto.username());
        }

        if (userRepo.existsByEmail(registerDto.email())) {
            error.put("email",registerDto.email());
        }

        return error;
    }

    @Override
    @Transactional
    public JwtResponse login(LoginDto loginDto) throws Exception {
        //Verifica la autenticacion
        authenticate(loginDto.username(), loginDto.password());

        //Si está autenticado carga los datos de usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.username());
        //Genera el token

        return new JwtResponse(jwtTokenUtil.generateToken(userDetails));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID CREDENTIALS",e);
        }
    }
}
