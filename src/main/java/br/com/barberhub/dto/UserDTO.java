package br.com.barberhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UserDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telephone,
        @NotBlank(message = "Password is required")
        String password
) {
}
