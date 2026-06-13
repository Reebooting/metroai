package com.metroai.metroai_backend.auth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.auth.dto.RegisterRequest;
import com.metroai.metroai_backend.auth.entity.Role;
import com.metroai.metroai_backend.auth.entity.User;
import com.metroai.metroai_backend.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password()) // plain text for now
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}