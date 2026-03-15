package org.gdiazm.finance.app.finance.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.expiration}")
    private long expiration;

    //private final String SECRET_KEY = UUID.randomUUID().toString();

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        String subject = extractClaims(token).getSubject();
        return UUID.fromString(subject);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
