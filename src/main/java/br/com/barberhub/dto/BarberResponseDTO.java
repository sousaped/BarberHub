package br.com.barberhub.dto;

import br.com.barberhub.entities.Barber;
import br.com.barberhub.enums.Specialty;

import java.util.List;

public record BarberResponseDTO(
        Long id,
        String name,
        List<Specialty> specialty,
        Boolean active,
        Double rating
) {

    public BarberResponseDTO(Barber barber){
        this(
                barber.getId(),
                barber.getName(),
                barber.getSpecialty(),
                barber.getActive(),
                barber.getRating()
        );
    }


}
