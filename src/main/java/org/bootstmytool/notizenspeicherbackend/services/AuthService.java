package org.bootstmytool.notizenspeicherbackend.services;

import org.bootstmytool.notizenspeicherbackend.model.User;
import org.bootstmytool.notizenspeicherbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    public boolean authenticate(String username, String password) {
        System.out.println("Attempting login for user: " + username);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Found user: " + user.getUsername() + " with password: " + user.getPassword());

            if (user.getPassword().equals(password)) {
                System.out.println("Password matched.");
                return true;
            } else {
                System.out.println("Password mismatch.");
            }
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
        newUser.setPassword(password);  // Ideally, hash the password here

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