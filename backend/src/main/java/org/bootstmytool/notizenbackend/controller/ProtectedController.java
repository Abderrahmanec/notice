package org.bootstmytool.notizenbackend.controller;

import org.bootstmytool.notizenbackend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ProtectedController {

    private static final Logger logger = LoggerFactory.getLogger(ProtectedController.class); // Logger to log events
    private final JwtService jwtService; // Service to handle JWT operations

    // Constructor injection of the JwtService
    @Autowired
    public ProtectedController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/api/protected/data")
    public ResponseEntity<?> getProtectedData(@RequestHeader("Authorization") String authHeader) {
        // Log the incoming request
        logger.info("Received request to access protected data");

        // Check if Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token from the header

            try {
                // Validate token and extract username
                String username = jwtService.extractUsername(token);

                // If username is null or token is expired
                if (username != null && !jwtService.isTokenExpired(token)) {
                    logger.info("Token is valid for user: {}", username);
                    return ResponseEntity.ok("Protected data for user: " + username);
                } else {
                    logger.warn("Invalid or expired token for user: {}", username);
                    return ResponseEntity.status(401).body("Invalid or expired token.");
                }
            } catch (Exception e) {
                logger.error("Error extracting username or validating token", e);
                return ResponseEntity.status(401).body("Invalid or expired token.");
            }

        } else {
            // Log if Authorization header is missing or incorrect
            logger.warn("Authorization header is missing or invalid");
            return ResponseEntity.status(400).body("Authorization header is missing or invalid.");
        }
    }
}
