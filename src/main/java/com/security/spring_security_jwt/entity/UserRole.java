package com.security.spring_security_jwt.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many-to-one relationship to Role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
