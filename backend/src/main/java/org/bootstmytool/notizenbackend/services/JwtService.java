package org.bootstmytool.notizenbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    // Get the signing key for HMAC algorithm
    private Key getSigningKey() {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("The secret key must be at least 32 characters long");
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract any claim from the JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        System.out.println("token: "+token);

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse the JWT and extract all claims
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT token has expired", e);
        } catch (MalformedJwtException e) {
            System.out.println("Malformed  token"+token);
            throw new InvalidTokenException("Malformed JWT token", e);
        } catch (SignatureException e) {
            throw new InvalidTokenException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new InvalidTokenException("Error extracting claims from JWT", e);
        }
    }


    // Check if the token has expired
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generate a JWT token for a username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the JWT token with the provided UserDetails
    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    // Extract UserId from token
    public String extractUserIdFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract token from Authorization header
    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


    // Custom exception for invalid tokens
    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public boolean isValidTokenFormat(String token) {
        if (token == null || token.split(".//").length != 3) {
            throw new InvalidTokenException("Malformed JWT token: incorrect number of parts", null);
        }
        return true;
    }


     class TokenExpiredException extends RuntimeException {
        public TokenExpiredException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}