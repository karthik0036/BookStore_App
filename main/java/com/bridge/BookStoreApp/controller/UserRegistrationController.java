package com.bridge.BookStoreApp.controller;


import com.bridge.BookStoreApp.dto.LoginDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.dto.UserRegistrationDto;
import com.bridge.BookStoreApp.exception.UserRegistrationException;
import com.bridge.BookStoreApp.model.UserRegistrationData;
import com.bridge.BookStoreApp.service.IUserRegistrationService;
import com.bridge.BookStoreApp.util.TokenUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * In Controller class we write the API's here
 */
@RestController
@RequestMapping("/userRegistrationService")
@Slf4j
public class UserRegistrationController {

    /**
     * Autowired IUserService to inject its dependency here
     */

    @Autowired
    IUserRegistrationService service;

    @Autowired
    TokenUtil tokenUtil;

    // Ability to call api to retrieve all user records
    //Get All Users
    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getUserData() {
        List<UserRegistrationData> usersList = service.getUserDetails();
        ResponseDTO response = new ResponseDTO("Get call success", usersList);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }


    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDTO> getContactDataById(@PathVariable("userId") int userId) {
        UserRegistrationData userDetails = service.getUserById(userId);
        ResponseDTO response = new ResponseDTO("Get call success for id", userDetails);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    //Ability to call api to register user record
    //Add User

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserRegistrationData(@Valid @RequestBody UserRegistrationDto userDTO) {
        String userDetails = service.userRegistration(userDTO);
        log.debug("User Registration input details: " + userDTO.toString());
        ResponseDTO response = new ResponseDTO("successfully Registered the user, Verification mail sent", userDetails);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }




    //Ability to call api to retrieve All user records by token
    @GetMapping(value = "/getAll/{token}")
    public ResponseEntity<ResponseDTO> getAllUserDataByToken(@PathVariable String token)
    {
        List<UserRegistrationData> listOfUser = service.getAllUserDataByToken(token);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:",listOfUser);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @GetMapping("/verify/{token}")
    ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token) {
        String userVerification = service.verifyUser(token);
        if (userVerification != null) {
            ResponseDTO responseDTO = new ResponseDTO("User verified :", userVerification);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("User Not verified data:", userVerification);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

    //Ability to call api to login user
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDto userLoginDTO) {
        return new ResponseEntity<ResponseDTO>(service.loginUser(userLoginDTO), HttpStatus.OK);
    }

    //Ability to call api to update User By id
    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDTO> updateContactData(@PathVariable("userId") int userId,
                                                         @Valid @RequestBody UserRegistrationDto userDTO) {
        UserRegistrationData updateUser = service.updateUserRegistrationData(userId, userDTO);
        log.debug("User Registration Data After Update " + updateUser.toString());
        ResponseDTO response = new ResponseDTO("Updated contact data for" + userId, updateUser);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("userId") int userId) {
        service.deleteUser(userId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + userId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }


    //Ability to call api to get token if forgot password
    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        String generatedToken=service.getToken(email);
        ResponseDTO responseDTO=new ResponseDTO("Token for mail id sent on mail successfully",generatedToken);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}
