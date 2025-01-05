package org.bootstmytool.notizenbackend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.bootstmytool.notizenbackend.model.User;
import org.bootstmytool.notizenbackend.netwrok.JwtSecretGenerator;
import org.bootstmytool.notizenbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Value("${jwt.secret}")
    private String secretKey;

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

    public boolean registerUser(String username, String password) {
        System.out.println("Attempting to register user: " + username);

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            System.out.println("Username already exists.");
            return false;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(newUser);
            System.out.println("User registered successfully: " + username);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

    public String extractUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}