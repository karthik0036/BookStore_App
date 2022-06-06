package com.bridge.BookStoreApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    @Email
    public String email;
    @NotEmpty(message = "Password cant be null")
    public String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}