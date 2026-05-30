package br.com.barberhub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartMenu() {
        return new OpenAPI()
                .info(
                        new Info().title("BarberHub API")
                                .description("Projeto pessoal desenvolvido com o objetivo de consolidar, na prática, os conhecimentos adquiridos por meio dos cursos da plataforma Alura e da Pós-Graduação em Arquitetura de Desenvolvimento Java pela FIAP, com foco na aplicação de boas práticas, padrões de projeto e soluções voltadas ao desenvolvimento backend.  ")
                                .version("v1.0.0")
                                .license(new License().name("Version 1.0")
                                ));
    }
}
