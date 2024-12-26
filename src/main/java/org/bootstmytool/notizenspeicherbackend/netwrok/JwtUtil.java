package org.bootstmytool.notizenspeicherbackend.netwrok;
import org.bootstmytool.notizenspeicherbackend.model.User;



import java.util.Date;

public class JwtUtil {
/*/

    private String secretKey = "secret";  // This should be stored securely

    // Generate a new token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) // Set the username as the subject
                .setIssuedAt(new Date()) // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set expiration time (1 hour)
                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign with HS256 and the secret key
                .compact(); // Return the compact token
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // Set the signing key
                .parseClaimsJws(token) // Parse the token
                .getBody() // Get the body
                .getSubject(); // Return the subject (username)
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare token expiration date with the current date
    }

    // Extract the expiration date from the token
    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration(); // Return the expiration date
    }
    /*/
}
