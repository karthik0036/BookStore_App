package com.bridge.BookStoreApp.model;


import com.bridge.BookStoreApp.dto.UserRegistrationDto;
//import javax.persistence.*;
import jakarta.persistence.*;
import lombok.Data;

//The @Entity annotation specifies that the class is an entity and is mapped to a database table

@Entity
@Table(name = "userregistration")
@Data
public class UserRegistrationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "email_id")
    private String email;

    @Column(name = "address")
    private String address;

    @Column
    private String password;

    public UserRegistrationData() {

    }

    public void createUser(UserRegistrationDto userDTO) {

        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }

    public void updateUser(UserRegistrationDto userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }

}



