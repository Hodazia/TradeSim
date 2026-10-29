package com.example.bcknd.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="app_users", // if u write User it will throw an error
uniqueConstraints = @UniqueConstraint(
        name="uk_app_users_email",
        columnNames = "email"
        ))
@Getter
@Setter
@NoArgsConstructor
public class AppUser extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="password_hash" ,nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled = true;
}
