package com.restapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    };

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Bipin Backend Project")
                                .version("1.0.0.1")
                                .description("Backend Development")
                                .termsOfService("17.91.1 Certfified Project X")
                                .contact(
                                        new io.swagger.v3.oas.models.info.Contact()
                                                .name("Bipin Shrestha")
                                                .email("bipinshrestha830@gmail.com")
                                                .url("bipinshrestha01.com.np")


                                )
                );
    }


}
