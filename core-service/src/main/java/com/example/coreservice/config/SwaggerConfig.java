package com.example.coreservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI coreServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Core Service API")
                        .description("API pour la gestion des favoris, avis, historique et catégories")
                        .version("1.0"));
    }
}