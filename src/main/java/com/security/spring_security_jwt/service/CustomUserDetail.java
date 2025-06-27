package com.security.spring_security_jwt.service;

import com.security.spring_security_jwt.entity.User;
import com.security.spring_security_jwt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetail implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetail(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User name not found"+ username));
        //Map the roles to authorities
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getRoles().stream().map(role ->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
