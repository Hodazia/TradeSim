package com.example.bcknd.Entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")  // if u write User it will throw an error
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private LocalDateTime createdAt;


}
