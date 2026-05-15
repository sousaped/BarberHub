package br.com.barberhub.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewDTO(
        @Min(value = 1)
        @Max(value = 5)
        Double rating,
        @NotBlank
        String comment

) {
}
