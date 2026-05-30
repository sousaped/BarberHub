package br.com.barberhub.dto;

import br.com.barberhub.enums.Specialty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BarberUpdateDTO(
        @Size(min = 1, message = "Specialty list must not be empty")
        List<Specialty> specialty


) {
}
