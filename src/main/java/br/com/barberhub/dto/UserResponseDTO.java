package br.com.barberhub.dto;

import br.com.barberhub.entities.User;
import br.com.barberhub.enums.Role;

import java.time.LocalDateTime;


public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String telephone,
        LocalDateTime lastChangeDate,
        Boolean active,
        Role role
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getLastChangeDate(),
                user.getActive(),
                user.getRole()
        );
    }
}
