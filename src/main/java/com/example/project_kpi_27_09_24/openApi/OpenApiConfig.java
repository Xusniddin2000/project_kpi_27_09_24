package com.example.project_kpi_27_09_24.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.example.project_kpi_27_09_24.controller")
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenapi(){
        return new OpenAPI()
                .info(new Info().title("Api dokument Kpi App uchun").version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("customHeaderAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("customHeaderAuth",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Custon Auht header")));

    }
}
