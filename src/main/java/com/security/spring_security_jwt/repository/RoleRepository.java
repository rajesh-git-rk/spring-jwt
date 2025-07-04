package com.security.spring_security_jwt.repository;

import com.security.spring_security_jwt.entity.Role;
import com.security.spring_security_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
