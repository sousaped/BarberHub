package br.com.barberhub.dto;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
        @Email(message = "Invalid email format")
        String email,
        String telephone
) {
}
