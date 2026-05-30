package br.com.barberhub.enums;

import br.com.barberhub.exceptions.BadRequestException;

public enum Specialty {

    HAIRCUT,
    BEARD_TRIM,
    EYEBROW_DESIGN,
    LINE_UP,
    HAIR_REMOVAL;

    public static Specialty fromString(String value) {
        for (Specialty specialty : Specialty.values()) {
            if (specialty.name().equalsIgnoreCase(value)) {
                return specialty;
            }
        }
        throw new BadRequestException("Invalid specialty: '" + value + "'. Allowed values: HAIRCUT, BEARD_TRIM, EYEBROW_DESIGN, LINE_UP, HAIR_REMOVAL");
    }
}

