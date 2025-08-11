package com.prathama.expensetracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI expenceTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker API")
                        .description("")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Prathama")
                                .email("prathama@eg.com")));
    }
}
