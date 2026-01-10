package com.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long ttlSeconds;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.ttl-seconds:3600}") long ttlSeconds
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.ttlSeconds = ttlSeconds;
    }

    public String issueToken(long ownerUserId, String googleSub) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);

        return Jwts.builder()
                .issuer("auth-service")
                .subject(Long.toString(ownerUserId))
                .claim("ownerUserId", ownerUserId)
                .claim("googleSub", googleSub)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key) // HS256 inferred
                .compact();
    }
}
