package com.metroai.metroai_backend.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}