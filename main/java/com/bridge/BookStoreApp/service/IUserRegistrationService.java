package com.bridge.BookStoreApp.service;

import com.bridge.BookStoreApp.dto.LoginDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.dto.UserRegistrationDto;
import com.bridge.BookStoreApp.model.UserRegistrationData;

import java.util.List;
import java.util.Optional;

/**
 * Created IUserRegistrationService interface to achieve abstraction
 */
public interface IUserRegistrationService {
    List<UserRegistrationData> getUserDetails();

    UserRegistrationData getUserById(int userId);

    String userRegistration(UserRegistrationDto userDTO);

    UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDto userDTO);

    void deleteUser(int userId);

    //Optional<UserRegistrationData> getAllUsersData(String token);

    List<UserRegistrationData> getAllUserDataByToken(String token);

    String verifyUser(String token);

    ResponseDTO loginUser(LoginDto loginDto);

    String getToken(String email);


}
