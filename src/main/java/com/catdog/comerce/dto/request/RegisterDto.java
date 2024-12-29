package com.catdog.comerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @Size(min = 4,max = 12,message = "Username need between 4 or 12 characters") @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password,
        @NotBlank(message = "Email is required") @Email(message = "Format email format invalid") String email) {
}
