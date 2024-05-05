package edu.duke.ece651.team4.TrackerServer.service;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        return JWT.create()
            .withSubject(user.getUsername())
            .withIssuedAt(now)
            .withExpiresAt(expiryDate)
            .withIssuer("Attendance-Tracker")
            .sign(Algorithm.HMAC512("SecretKey".getBytes()));
    }

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
