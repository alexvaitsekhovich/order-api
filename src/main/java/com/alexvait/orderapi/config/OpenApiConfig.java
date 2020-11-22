package com.alexvait.orderapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Ordering API",
                version = "v1",
                description = "REST API for order placement",
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
public class    OpenApiConfig {
}
