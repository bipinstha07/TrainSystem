package com.restapi.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5172","http://localhost:5121"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Content-type"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(Customizer.withDefaults())
        httpSecurity.csrf(cs->cs.disable())
//                .cors(Customizer.withDefaults())
                .cors(c->c.configurationSource(configurationSource()))
                .authorizeHttpRequests(request->
                        request.requestMatchers("/auth/login","/auth/**","/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/api/v1/user/**",
                                        "/*",
                                        "/swagger-resources/**",
                                        "/webjars/**")


                                .permitAll()
                                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                               .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .anyRequest()
//                                .permitAll()
                                .authenticated()
                );

        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.exceptionHandling(e->e.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
