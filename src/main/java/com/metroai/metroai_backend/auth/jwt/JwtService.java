package com.metroai.metroai_backend.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

private SecretKey getSigningKey() {

    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)
    );
}
public String generateToken(
        String email,
        String role
) {

    return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(
                    new Date(
                            System.currentTimeMillis()
                                    + jwtExpiration
                    )
            )
            .signWith(getSigningKey())
            .compact();
}


private Claims extractAllClaims(String token) {

    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

public String extractEmail(String token) {

    Claims claims = extractAllClaims(token);

    return claims.getSubject();
}

public boolean isTokenValid(String token) {

    try {

        Claims claims =
                extractAllClaims(token);

        return claims.getExpiration()
                .after(new Date());

    } catch (Exception e) {

        return false;
    }
}

}