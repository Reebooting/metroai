package com.metroai.metroai_backend.auth.dto;

public record UserProfileResponse(
        String email,
        String role
) {
}