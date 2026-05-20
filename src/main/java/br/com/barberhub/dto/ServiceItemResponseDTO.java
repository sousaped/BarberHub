package br.com.barberhub.dto;

import br.com.barberhub.entities.ServiceItem;
import br.com.barberhub.enums.Specialty;

import java.math.BigDecimal;

public record ServiceItemResponseDTO(

        Long id,
        Specialty name,
        BigDecimal price,
        Integer durationInMinutes

) {

    public ServiceItemResponseDTO (ServiceItem serviceItem){
        this(
                serviceItem.getId(),
                serviceItem.getName(),
                serviceItem.getPrice(),
                serviceItem.getDurationInMinutes());
    }
}
