package br.com.barberhub.dto;

import br.com.barberhub.enums.Specialty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BarberRequestDTO(
        @NotNull(message = "User is required")
        Long userId,

        @NotNull(message = "Specialty is required")
        List<Specialty> specialty
) {
}
