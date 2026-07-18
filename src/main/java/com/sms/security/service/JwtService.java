package com.sms.security.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // =====================================
    // JWT CONFIGURATION
    // =====================================

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12345";

    private static final long ACCESS_TOKEN_EXPIRATION =
            1000L * 60 * 60; // 1 Hour

    private static final long REFRESH_TOKEN_EXPIRATION =
            1000L * 60 * 60 * 24 * 7; // 7 Days

    // =====================================
    // GENERATE ACCESS TOKEN
    // =====================================

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()

                .subject(userDetails.getUsername())

                .claim("role", userDetails.getAuthorities())

                .issuedAt(new Date())

                .expiration(
                        new Date(System.currentTimeMillis()
                                + ACCESS_TOKEN_EXPIRATION)
                )

                .signWith(getSignKey())

                .compact();
    }

    // =====================================
    // GENERATE REFRESH TOKEN
    // =====================================

    public String generateRefreshToken(UserDetails userDetails) {

        return Jwts.builder()

                .subject(userDetails.getUsername())

                .issuedAt(new Date())

                .expiration(
                        new Date(System.currentTimeMillis()
                                + REFRESH_TOKEN_EXPIRATION)
                )

                .signWith(getSignKey())

                .compact();
    }

    // =====================================
    // ACCESS TOKEN EXPIRY (SECONDS)
    // =====================================

    public long getAccessTokenExpirationInSeconds() {

        return ACCESS_TOKEN_EXPIRATION / 1000;
    }

    // =====================================
    // EXTRACT USERNAME
    // =====================================

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // =====================================
    // EXTRACT CLAIM
    // =====================================

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    // =====================================
    // EXTRACT ALL CLAIMS
    // =====================================

    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSignKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    // =====================================
    // VALIDATE TOKEN
    // =====================================

    public boolean isTokenValid(
            String token,
            String username) {

        String tokenUsername = extractUsername(token);

        return tokenUsername.equals(username)
                && !isTokenExpired(token);
    }

    // =====================================
    // CHECK TOKEN EXPIRATION
    // =====================================

    private boolean isTokenExpired(String token) {

        Date expiration = extractClaim(
                token,
                Claims::getExpiration
        );

        return expiration.before(new Date());
    }

    // =====================================
    // SIGNING KEY
    // =====================================

    private SecretKey getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }
}