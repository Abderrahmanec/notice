package org.bootstmytool.backend.service;

import org.bootstmytool.backend.model.User;
import org.bootstmytool.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        System.out.println("Attempting login for user: " + username);
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    // Method to register a new user in the database
        public boolean registerUser(String username, String password) {
        System.out.println("Attempting to register user: " + username);

        // Check if the username already exists in the database
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            System.out.println("Username already exists.");
            return false;
        }

        // Create a new user object
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));  // Ideally, hash the password here

        // Save the new user to the database
        try {
            userRepository.save(newUser);
            System.out.println("User registered successfully: " + username);

            return true;
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }


}