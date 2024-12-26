package org.bootstmytool.notizenspeicherbackend.netwrok;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bootstmytool.notizenspeicherbackend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtAuthenticationFilter implements HandlerInterceptor {

    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid token");
            return false;
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            // Validate token
            String username = jwtService.extractUsername(token);

            // Optionally set user info in request context (e.g., for use in controllers)
            request.setAttribute("username", username);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or expired token");
            return false;
        }
    }
}
