package com.restapi.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title="Bipin Backend Project",
                                version = "1.0.0.2",
                                description = "Backend Development",
                                termsOfService = "17.91.1 Certified Project BPN",
        contact = @Contact(
                name = "Bipin Shrestha",
                email = "bipinshrestha830@gmail.com",
                url = "bipinshrestha01.com.np"
        )
)
        ,
        security = @SecurityRequirement(name = "bearerAuth")
)

@SecurityScheme(
        name = "bearerAuth",
        type= SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class OpenAPIConfig {



//    @Bean
//    public OpenAPI openAPI(){
//        return new OpenAPI()
//                .info(
//                        new Info()
//                                .title("Bipin Backend Project")
//                                .version("1.0.0.1")
//                                .description("Backend Development")
//                                .termsOfService("17.91.1 Certfified Project X")
//                                .contact(
//                                        new io.swagger.v3.oas.models.info.Contact()
//                                                .name("Bipin Shrestha")
//                                                .email("bipinshrestha830@gmail.com")
//                                                .url("bipinshrestha01.com.np")
//
//
//                                )
//                );
//    }
}
