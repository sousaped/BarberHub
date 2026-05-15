package br.com.barberhub.dto;

import br.com.barberhub.enums.Specialty;

import java.util.List;

public record BarberUpdateDTO(
        Boolean active,
        List<Specialty> specialty


) {
}
