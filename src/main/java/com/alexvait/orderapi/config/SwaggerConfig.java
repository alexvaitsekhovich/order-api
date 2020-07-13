package com.alexvait.orderapi.config;

import com.alexvait.orderapi.controller.OrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

// http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("Order controller", "Order API endpoints"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex(OrderController.BASE_URL + ".*"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "Alex Vaitsekhovich",
                "https://github.com/alexvaitsekhovich",
                "alex.vaitsekhovich@gmail.com");

        return new ApiInfoBuilder().title("Order API")
                .description("Order API for sample microservice")
                .contact(contact).version("1.0").build();
    }

}
