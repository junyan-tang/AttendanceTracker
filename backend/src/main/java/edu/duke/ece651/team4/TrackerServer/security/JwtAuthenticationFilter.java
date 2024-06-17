package edu.duke.ece651.team4.TrackerServer.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
     try {
            System.out.println("JwtAuthenticationFilter");

            String jwt = getJwtFromRequest(request);
            System.out.println("jwt: " + jwt);
            if (jwt != null && validateToken(jwt)) {
                String username = getUsernameFromJWT(jwt);
                System.out.println("username: " + username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        System.out.println(request.toString());
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String authToken) {
    try {
        Algorithm algorithm = Algorithm.HMAC512("SecretKey".getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer("Attendance-Tracker")
                                  .build();
        verifier.verify(authToken);
        return true; 
    } catch (JWTVerificationException exception) {
        System.out.println("JWTVerificationException"+exception.getMessage());
        return false; 
    }
}
private String getUsernameFromJWT(String token) {
    try {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject(); 
    } catch (JWTDecodeException exception){
        return null;
    }
}
}
