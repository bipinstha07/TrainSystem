package com.restapi.dto;

public record ErrorResponse(String message, int code,boolean success) {
}
