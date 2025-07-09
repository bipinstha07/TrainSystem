package com.restapi.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String username = null;
        String token = null;

        // Check if the Authorization header exists and starts with "Bearer " (case-insensitive)
        if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("bearer ")) {
            try {
                // Extract the token by removing the "Bearer " prefix
                token = authorizationHeader.substring(7);
                // Extract username from the token
                username = jwtHelper.getUserNameFromToken(token);

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
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("Unable to get JWT Token: " + ex.getMessage());
                // ex.printStackTrace(); // Avoid printing full stack trace in production logs unless necessary
            } catch (ExpiredJwtException ex) {
                System.err.println("JWT Token has expired: " + ex.getMessage());
                // ex.printStackTrace();
            } catch (MalformedJwtException ex) {
                System.err.println("Invalid JWT Token: " + ex.getMessage());
                // ex.printStackTrace();
            } catch (Exception e) {
                System.err.println("An unexpected error occurred during token processing: " + e.getMessage());
                // e.printStackTrace();
            }
        } else {
            // This message is expected for public endpoints like /auth/login
            // and for any request without a proper Authorization header.
            System.out.println("Invalid or missing Authorization Header for request to: " + request.getRequestURI());
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
