package com.security.spring_security_jwt.controller;

import com.security.spring_security_jwt.Security.JwtUtil;
import com.security.spring_security_jwt.dto.RegisterRequest;
import com.security.spring_security_jwt.entity.Role;
import com.security.spring_security_jwt.entity.User;
import com.security.spring_security_jwt.repository.RoleRepository;
import com.security.spring_security_jwt.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Register user
    @PostMapping("/register") //Register endpoint
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){

        //Check if username already exists
        if(userRepository.findByUserName(registerRequest.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("User name already exist");
        }

        User newUser = new User();
        newUser.setUserName(registerRequest.getUsername());

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        System.out.println(encodedPassword);
        newUser.setPassword(encodedPassword);

        //convert role names to role entities and assign to user
        Set<Role> roles = new HashSet<>();
        for(String roleName : registerRequest.getRoles()){
            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found:"+ roleName));
            roles.add(role);
        }
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return ResponseEntity.ok("User Registered Successfully");
    }

    //Login API

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        } catch(Exception e){
            System.out.println("Exception: "+ e);
        }
        String token = jwtUtil.generateToken(loginRequest.getUserName());
        return ResponseEntity.ok(token);
    }
}
