package com.catdog.comerce.security.service;

import com.catdog.comerce.entity.User;
import com.catdog.comerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepo.findOneByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRole().stream().forEach(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(role.getType().getValue()));
            grantedAuthorities.add(simpleGrantedAuthority);
        });

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),user.isEnabled(),user.isAccountNoExpired(),user.isCredentialNoExpired(),user.isAccountNoLocked(),grantedAuthorities);
    }
}
