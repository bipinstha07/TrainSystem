package com.restapi.dto;

public record LoginRequest(
        String username,
        String password
) {
}
