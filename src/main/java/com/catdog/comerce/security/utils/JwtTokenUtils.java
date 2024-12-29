package com.catdog.comerce.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

    private final long EXPIRATION_TIME = 5*60*60*1000;//5 horas

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());

        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .compact();
    }

    public Claims getAllClaims(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }
}
