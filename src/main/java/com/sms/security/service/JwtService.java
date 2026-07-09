package com.sms.security.service;

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

	private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey12345";

	// Generate JWT Token

	public String generateToken(UserDetails userDetails) {

		return Jwts.builder()

				.subject(userDetails.getUsername())

				.claim("role", userDetails.getAuthorities())

				.issuedAt(new Date())

				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))

				.signWith(getSignKey())

				.compact();

	}

	// Extract username from JWT

	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);

	}

	// Extract any claim

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {

		final Claims claims = extractAllClaims(token);

		return resolver.apply(claims);

	}

	// Extract all claims

	private Claims extractAllClaims(String token) {

		return Jwts.parser()

				.verifyWith(getSignKey())

				.build()

				.parseSignedClaims(token)

				.getPayload();

	}

	// Validate Token

	public boolean isTokenValid(String token, String username) {

		String tokenUsername = extractUsername(token);

		return (tokenUsername.equals(username) && !isTokenExpired(token));

	}

	// Check expiration

	private boolean isTokenExpired(String token) {

		Date expiration = extractClaim(token, Claims::getExpiration);

		return expiration.before(new Date());

	}

	private SecretKey getSignKey() {

		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	}

}