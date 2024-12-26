package org.bootstmytool.notizenspeicherbackend.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Generate a secure 256-bit key for HMAC-SHA256
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Method to generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set the username as the subject of the token
                .setIssuedAt(new Date()) // Set the token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set expiration to 1 hour
                .signWith(SECRET_KEY) // Sign with the key and specify HS256
                .compact();
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Same key used to sign the token
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // Should return the username
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired");
            throw new RuntimeException("Token has expired");
        } catch (JwtException e) {
            System.out.println("JWT validation error: " + e.getMessage());
            throw new RuntimeException("Invalid token");
        }
    }



    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare expiration date with current date
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        JwtParser parser = Jwts.parser() // Correct method to create a JwtParser
                .setSigningKey(SECRET_KEY) // Set the signing key used for validation
                .build(); // Build the parser

        return parser.parseClaimsJws(token)
                .getBody()
                .getExpiration(); // Get the expiration date from the token's body
    }
}
