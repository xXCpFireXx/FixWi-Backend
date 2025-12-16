package com.fixwi.fixwi_backend.infrastructure.security.adapter;

import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.out.security.TokenProviderPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("fullName", user.getFullName());
        claims.put("role", user.getRole().name());
        claims.put("email", user.getEmail());

        // 2. Build the token
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // --- Validation and Utility Methods ---

    /**
     * Validates the integrity and expiration of a JWT.
     * @param token The JWT string to validate.
     * @return True if the token is valid, false otherwise (expired, invalid signature).
     */

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getSpecificClaim(String token, String claimName, Class<T> type) {
        final Claims claims = getAllClaims(token);
        return claims.get(claimName, type);
    }

    /**
     * Generic method to resolve a claim using a function (e.g., Claims::getExpiration).
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates the SecretKey object from the string secret.
     * Uses HMAC SHA to create a secure key.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}