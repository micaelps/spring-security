package com.micaelps.dogs.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.micaelps.dogs.validators.UniqueValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserSystemRequest {

    @Email
    @NotBlank
    @UniqueValue(fieldName = "email", domainClass = UserSystem.class)
    @JsonProperty
    private String email;
    @NotBlank
    @Size(min = 6)
    @JsonProperty
    private String password;


    public NewUserSystemRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserSystem toModel() {
        String encodedPassword = new BCryptPasswordEncoder().encode(this.password);
        return new UserSystem(this.email, encodedPassword);
    }


}
