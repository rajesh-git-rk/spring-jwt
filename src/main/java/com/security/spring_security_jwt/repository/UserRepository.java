package com.security.spring_security_jwt.repository;

import com.security.spring_security_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   Optional<User> findByUserName(String name);
}
