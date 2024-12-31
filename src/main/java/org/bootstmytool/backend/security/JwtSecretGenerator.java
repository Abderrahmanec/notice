package org.bootstmytool.backend.security;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    // Generate a random secret key (you can adjust the length as needed)
    public static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKey = new byte[32]; // 256-bit key
        secureRandom.nextBytes(secretKey);
        return Base64.getEncoder().encodeToString(secretKey);
    }

    // Retrieve the secret key from a safe environment variable or configuration
    public static String getSecretKey() {
        String secretKey = System.getenv("JWT_SECRET_KEY");
        if (secretKey == null || secretKey.isEmpty()) {
            // If the key is not set, generate a new one (or handle accordingly)
            secretKey = generateSecretKey();
        }
        return secretKey;
    }
}
