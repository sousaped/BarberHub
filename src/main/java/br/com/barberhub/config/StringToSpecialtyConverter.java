package br.com.barberhub.config;

import br.com.barberhub.enums.Specialty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSpecialtyConverter implements Converter<String, Specialty> {

    @Override
    public Specialty convert(String value) {
        return Specialty.fromString(value);
    }
}
