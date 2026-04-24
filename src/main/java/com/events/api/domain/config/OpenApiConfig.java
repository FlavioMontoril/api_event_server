package com.events.api.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI custonOpenAPI() {
        return new OpenAPI()
                .info(new Info()

                        .title("Events Management API")
                        .version("1.0")
                        .description("API para gerenciamento de eventos técnicos, cupons e localizações.")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seu.email@exemplo.com")));
    }

}
