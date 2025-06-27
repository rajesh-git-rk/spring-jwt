package com.security.spring_security_jwt.controller;

import com.security.spring_security_jwt.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value(("${role.user}"))
    private String roleUser;

    //Endpoint to access user protected data
    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7); //Remove bearer prefix
            try {
                if (jwtUtil.isTokenValid(jwtToken)) {
                    String userName = jwtUtil.extractUserName(jwtToken);
                    //extract role from the JWT Token
                    Set<String> roles = jwtUtil.extractRoles(jwtToken);
                    if (roles.contains(roleAdmin)) {
                        return ResponseEntity.ok("Welcome " + userName + "Here is the " + roles + "_specific data");
                    } else if (roles.contains(roleUser)) {
                        return ResponseEntity.ok("Welcome " + userName + "Here is the " + roles + "_specific data");
                    } else {
                        return ResponseEntity.status(403).body("Access denied");
                    }
                }
            } catch (Exception exception) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing or invalid");
    }

}
