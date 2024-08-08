package com.commerce.abm.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info= @Info(
                title= "E-COMMERCE API",
                version = "0.1",
                description = "CRUD of clients. product, cart and invoice. "
        )
)
public class OpenApi {
}
