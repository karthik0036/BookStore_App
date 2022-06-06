package com.bridge.BookStoreApp.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

//Data transfer object of UserData transfer object of UserRegistration  Model

@Data
@ToString
public class UserRegistrationDto {

    @Pattern(regexp = "^[A-Z]{1,}[a-zA-z\\s.]{2,}$")
    @NotEmpty(message = "Name can not be NULL")
    public String firstName;

    @Pattern(regexp = "^[A-Z]{1,}[a-zA-z\\s.]{2,}$")
    @NotEmpty(message = "Name can not be NULL")
    public String lastName;

    public String email;

    public String address;
    public String password;
}
