package org.bootstmytool.notizenspeicherbackend.controller;

import org.bootstmytool.notizenspeicherbackend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/login")
    public LoginResponse login(@RequestBody UserLoginRequest request) {
        System.out.println("Received login request: " + request.getUsername());
        System.out.println("Received login password"+request.getPassword());
        boolean authenticated = authService.authenticate(request.getUsername(), request.getPassword());
        if (authenticated) {
            return new LoginResponse(true, "Login successful");
        } else {
            return new LoginResponse(false, "Invalid credentials");
        }
    }

    // Endpoint for user registration
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



    // Make the inner class static
    public static class UserLoginRequest {
        private String username;
        private String password;

        // Getters and setters
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

    // Make the inner class static
    public static class LoginResponse {
        private boolean success;
        private String message;

        public LoginResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters and setters
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
    }
}
