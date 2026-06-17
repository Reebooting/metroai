package com.metroai.metroai_backend.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.auth.dto.LoginRequest;
import com.metroai.metroai_backend.auth.dto.LoginResponse;
import com.metroai.metroai_backend.auth.dto.RegisterRequest;
import com.metroai.metroai_backend.auth.dto.UserProfileResponse;
import com.metroai.metroai_backend.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
           @Valid @RequestBody LoginRequest request
) {
    return authService.login(request);
}

@GetMapping("/me")
public UserProfileResponse getCurrentUser(
        @RequestHeader("Authorization")
        String authHeader
) {

    String token = authHeader.substring(7);

    return authService.getCurrentUser(token);
}

}