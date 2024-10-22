package com.alura.foro.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// OpenAPI
@Configuration
public class SpringDocConfigurations {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))) // incluimos el token de autenticación
                .info(new Info() // añadidmos información adicional sobre la API
                        .title("API Alura.latam")
                        .description("API Rest del foro Alura.latam, que contiene las funcionalidades de CRUD de cursos, respuestas, tópicos y usuarios.")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@alura.latam"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://alura.latam/api/licencia")));
    }

    @Bean
    public void message() {
        System.out.println("bearer is working");
    }
}
