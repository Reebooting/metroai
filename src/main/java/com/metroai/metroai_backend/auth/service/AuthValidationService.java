package com.metroai.metroai_backend.auth.service;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.auth.entity.Role;
import com.metroai.metroai_backend.auth.entity.User;
import com.metroai.metroai_backend.auth.jwt.JwtService;
import com.metroai.metroai_backend.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthValidationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void validateAdmin(
            String token
    ) {

        String email =
                jwtService.extractEmail(
                        token
                );

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        if (
                user.getRole()
                        != Role.ADMIN
        ) {

            throw new RuntimeException(
                    "Admin access required"
            );
        }
    }
}