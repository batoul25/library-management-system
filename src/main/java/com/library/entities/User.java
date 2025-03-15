package com.library.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"

 
}