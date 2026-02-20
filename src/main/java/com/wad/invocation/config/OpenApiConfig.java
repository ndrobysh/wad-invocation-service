package com.wad.invocation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI invocationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API d'Invocations Gacha")
                        .description("Gère les invocations de monstres, les taux de probabilité, et la résilience (buffer).")
                        .version("1.0.0"));
    }
}
