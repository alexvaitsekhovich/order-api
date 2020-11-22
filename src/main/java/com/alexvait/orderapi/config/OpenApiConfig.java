package com.alexvait.orderapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Accounting API",
                version = "v1",
                description = "REST API for accounting, with user management and JWT token authentication",
                contact = @Contact(
                        name = "Alex Vait",
                        email = "alex.vaitsekhovich@gmail.com",
                        url = "https://github.com/alexvaitsekhovich"
                )

        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local host"
                )
        }
)
public class OpenApiConfig {
}
