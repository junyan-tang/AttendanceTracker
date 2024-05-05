package edu.duke.ece651.team4.TrackerServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team4.TrackerServer.dto.JwtAuthenticationResponse;
import edu.duke.ece651.team4.TrackerServer.service.TokenService;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getNetid(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenService.generateToken(authentication);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);
        System.out.println("Returning JWT" + response.getAccessToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login/error=true/")
    public String error() {
        return "error";
    }

    static class LoginRequest {
        private String netid;
        private String password;
        public LoginRequest() {
        }
        public String getNetid() {
            System.out.println(netid + " get netid");
            return netid;
        }
        public void setNetid(String netid) {
            System.out.println(netid + " set netid");
            this.netid = netid;
        }
        public String getPassword() {
            System.out.println(password + " get password");
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
