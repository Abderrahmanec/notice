package org.bootstmytool.backend.controller;

import org.bootstmytool.backend.service.AuthService;
import org.bootstmytool.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // the allowed origin explicitly
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    // Login endpoint
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        System.out.println("Received login request for username: " + request.getUsername());

        // Authenticate user
        boolean authenticated = authService.authenticate(request.getUsername(), request.getPassword());
        System.out.println("Authentication status for user " + request.getUsername() + ": " + authenticated);

        if (authenticated) {
            // Generate JWT token
            String token = jwtService.generateToken(request.getUsername());
            System.out.println("Generated token for user " + request.getUsername() + ": " + token);
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", token));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse(false, "Invalid credentials", null));
        }
    }

    // Registration endpoint
    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody UserCredentials credentials) {
        // Print out the registration request details
        System.out.println("Received registration request:");
        System.out.println("Username: " + credentials.getUsername());
        System.out.println("Password: " + credentials.getPassword());

        boolean registrationSuccess = authService.registerUser(credentials.getUsername(), credentials.getPassword());
        if (registrationSuccess) {
            return ResponseEntity.ok("Registration successful");
        } else {
            return ResponseEntity.status(400).body("Username already exists");
        }
    }

    // UserLoginRequest class
    public static class UserLoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // LoginResponse class
    public static class LoginResponse {
        private boolean success;
        private String message;
        private String token;

        public LoginResponse(boolean success, String message, String token) {
            this.success = success;
            this.message = message;
            this.token = token;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    // UserCredentials class
    public static class UserCredentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}