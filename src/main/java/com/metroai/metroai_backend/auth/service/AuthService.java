package com.metroai.metroai_backend.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.auth.dto.LoginRequest;
import com.metroai.metroai_backend.auth.dto.LoginResponse;
import com.metroai.metroai_backend.auth.dto.RegisterRequest;
import com.metroai.metroai_backend.auth.dto.UserProfileResponse;
import com.metroai.metroai_backend.auth.entity.Role;
import com.metroai.metroai_backend.auth.entity.User;
import com.metroai.metroai_backend.auth.jwt.JwtService;
import com.metroai.metroai_backend.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
  
   public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

    User user = userRepository
            .findByEmail(request.email())
            .orElseThrow(
                    () -> new RuntimeException(
                            "Invalid email or password"
                    )
            );

    boolean validPassword =
            passwordEncoder.matches(
                    request.password(),
                    user.getPassword()
            );

    if (!validPassword) {
        throw new RuntimeException(
                "Invalid email or password"
        );
    }

    String token = jwtService.generateToken(
        user.getEmail(),
        user.getRole().name()
);

return new LoginResponse(token);
}

public UserProfileResponse getCurrentUser(
        String token
) {

    String email =
            jwtService.extractEmail(token);

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "User not found"
                            )
                    );

    return new UserProfileResponse(
            user.getEmail(),
            user.getRole().name()
    );
}
}