package br.com.barberhub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServiceItemUpdateDTO(
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Duration is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationInMinutes
) {

}
