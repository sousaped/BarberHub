package br.com.barberhub.dto;

import br.com.barberhub.entities.User;

import java.time.LocalDateTime;


public record UserResponseDTO(
        Long id,
        String email,
        String telephone,
        LocalDateTime lastChangeDate
) {

    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getEmail(),
                user.getTelephone(),
                user.getLastChangeDate());

    }


}
