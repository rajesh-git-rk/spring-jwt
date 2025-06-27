package com.security.spring_security_jwt.Security;


import com.security.spring_security_jwt.entity.Role;
import com.security.spring_security_jwt.entity.User;
import com.security.spring_security_jwt.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    //Secret key
    private static final SecretKey secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    //expiration time
    private final int jwtExpirationMS = 86400000;
    private UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Generate Token
    public String generateToken(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        Set<Role> roles = user.get().getRoles();

        //Add roles to the token
        return Jwts.builder().setSubject(username).claim("roles", roles.stream().map(role -> role.getName())
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpirationMS))
                .signWith(secretkey).compact();
    }

    //Extract user name
    public String extractUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    //Extract Role
    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder().setSigningKey(secretkey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);
        return Set.of(rolesString);
    }

    //TokenValidation

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretkey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;

    }

}
}
