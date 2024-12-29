package com.catdog.comerce.security.config;

import com.catdog.comerce.dto.response.CustomeErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = (String) request.getAttribute("error");

        if (message == null) {
            message = "Authentication failed";
        }

        CustomeErrorResponse customeErrorResponse = new CustomeErrorResponse(LocalDateTime.now(),message,request.getRequestURI());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(customeErrorResponse));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(object);
    }
}
