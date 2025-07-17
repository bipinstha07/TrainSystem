package com.restapi.config.security;

import com.restapi.RestapiApplication;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger loggerSecurity = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private JwtHelper jwtHelper;
    private UserDetailsService userDetailsService;

    // Constructor for dependency injection
    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        loggerSecurity.trace("Request to JWT FILTER");
//        loggerSecurity.trace("Request to the JWT Filter: {}", authorizationHeader);

        String username = null;
        String token = null;

        // Check if the Authorization header exists and starts with "Bearer " (case-insensitive)
        if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("bearer ")) {
            try {
                // Extract the token by removing the "Bearer " prefix
                token = authorizationHeader.substring(7);
                // Extract username from the token
                username = jwtHelper.getUserNameFromToken(token);

//                loggerSecurity.trace("User name: {}",username);

                // If username is extracted and no authentication is currently set in SecurityContext
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Load UserDetails from the username
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Validate the token against the UserDetails
                    if (jwtHelper.isTokenValid(token, userDetails)) {
                        // Create an authentication object
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        // Set authentication details from the request
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Set the authentication object in the SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        loggerSecurity.trace("Authentication Security reached here");
                    }
                }
            } catch (IllegalArgumentException ex) {
                loggerSecurity.error("Illegal Argument : {}" , ex.getMessage());
                // ex.printStackTrace(); // Avoid printing full stack trace in production logs unless necessary
            } catch (ExpiredJwtException ex) {
                loggerSecurity.error("JWT Token expired : {}", ex.getMessage());
//                System.err.println("JWT Token has expired: " + ex.getMessage());
                // ex.printStackTrace();
            } catch (MalformedJwtException ex) {

                loggerSecurity.error("Invalid JWT Token : {}", ex.getMessage());
//                System.err.println("Invalid JWT Token: " + ex.getMessage());
                // ex.printStackTrace();
            } catch (Exception e) {
                loggerSecurity.error("Unexpected error during token processing : {}", e.getMessage());
//                System.err.println("An unexpected error occurred during token processing: " + e.getMessage());
                // e.printStackTrace();
            }
        } else {
            // This message is expected for public endpoints like /auth/login
            // and for any request without a proper Authorization header.
            loggerSecurity.error("Invalid or Missing Authorizatio Header for request to : {}", request.getRequestURI());
//            System.out.println("Invalid or missing Authorization Header for request to: " + request.getRequestURI());
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
