package org.bootstmytool.backend.controller;

import org.bootstmytool.backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    private final JwtService jwtService; // Service to handle JWT operations

    // Constructor injection of the JwtService
    public ProtectedController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/api/protected/data")
    public ResponseEntity<?> getProtectedData(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token

            String username = jwtService.extractUsername(token); // Validate token
            if (username != null) {
                return ResponseEntity.ok("Protected data for user: " + username);
            } else {
                return ResponseEntity.status(401).body("Invalid or expired token.");
            }
        } else {
            return ResponseEntity.status(400).body("Authorization header is missing or invalid.");
        }
    }

}
