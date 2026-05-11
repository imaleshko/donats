package com.donats.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class AccessTokenService {

    private final SecretKey key;
    private final long expirationTime;

    public AccessTokenService(
            @Value("${jwt.secretKey}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expirationTime = expirationTime;
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
        return claims.getSubject();
    }
}
