package br.com.barberhub.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReviewRequestDTO(
        @NotNull(message = "User is required")
        Long userId,

        @NotNull(message = "Barber is required")
        Long barberId,

        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        Double rating,

        @NotBlank(message = "Comment is required")
        String comment,

        @NotBlank
        LocalDateTime reviewDate
) {


}
