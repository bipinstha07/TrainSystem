package com.restapi.dto;

import org.springframework.security.core.userdetails.UserDetails;

public record JwtResponse(
        String token,
        String refreshToken,
        UserDto userDto
) {
}
