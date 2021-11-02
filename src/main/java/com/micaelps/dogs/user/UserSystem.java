package com.micaelps.dogs.user;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class UserSystem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    @PastOrPresent
    private final LocalDate createdAt = LocalDate.now();

    public UserSystem(@Email @NotBlank String email, @NotBlank @Size(min = 6) String password) {
        this.email = email;
        this.password = password;
    }

    @Deprecated
    public UserSystem() {
    }

    public String getPassword() {return password; }

    public String getEmail() {
        return this.email;
    }

    public Long getId() {return this.id; }
}
