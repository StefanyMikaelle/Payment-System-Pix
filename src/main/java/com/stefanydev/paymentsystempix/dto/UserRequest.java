package com.stefanydev.paymentsystempix.dto;

import com.stefanydev.paymentsystempix.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "The name can't be null!")
        @NotBlank(message = "The name can't be blank!")
        String name,

        @NotNull(message = "The email can't be null!")
        @NotBlank(message = "The email can't be blank!")
        String email,

        @NotNull(message = "The password can't be null!")
        @NotBlank(message = "The password can't be blank!")
        @Size(min = 10 , message = "The password must have at least 10 characters.")
        String password,

        @NotNull(message = "The role can't be null!")
        @NotBlank(message = "The role can't be blank!")
        String role) {
    public User toModel(){
       return new User(name, email, password, role);
    }
}
