package br.com.barberhub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record ServiceItemUpdateDTO(

        @DecimalMin(value = "0.0", message = "Price must be greater than 0")
        BigDecimal price,


        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationInMinutes
) {

}
