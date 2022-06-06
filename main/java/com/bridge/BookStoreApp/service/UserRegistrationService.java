package com.bridge.BookStoreApp.service;


import com.bridge.BookStoreApp.dto.LoginDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.dto.UserRegistrationDto;
import com.bridge.BookStoreApp.exception.UserRegistrationException;
import com.bridge.BookStoreApp.model.UserRegistrationData;
import com.bridge.BookStoreApp.repository.UserRegistrationRepository;
import com.bridge.BookStoreApp.util.Email;
import com.bridge.BookStoreApp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Created UserRegistrationService class to serve api calls done by controller layer
 */
@Service
@Slf4j
public class UserRegistrationService implements IUserRegistrationService {
    String token = null;
    /**
     * Autowired interface to inject its dependency here
     */
    @Autowired
    Email mailService;

    @Autowired
    TokenUtil tokenUtil;


    @Autowired
    UserRegistrationRepository userRepo;




    /**
     * create a method name as userRegistration
     * Ability to save user details to repository
     * */
    @Override
    public String userRegistration(UserRegistrationDto userDTO) {

        Optional<UserRegistrationData> userCheck = userRepo.findByEmailId(userDTO.getEmail());
        if (userCheck.isPresent()) {
            log.error("Email already exists");
            throw new UserRegistrationException("email already exists");
        } else {
            UserRegistrationData userData = new UserRegistrationData();
            userData.createUser(userDTO);
            userRepo.save(userData);
            token = tokenUtil.createToken(userData.getUserId());
            mailService.sendEmail(userData .getEmail(), "Test Email", "Registered SuccessFully, hii: "
                    + userData.getFirstName() + "Please Click here to get data-> "
                    + mailService.getLink(token));
            //return userData;
            return token;
        }
    }
    /**
     * create a method name as getUserDetails
     * - Ability to get all user data by findAll() method
     * */

    @Override
    public List<UserRegistrationData> getUserDetails() {

        return userRepo.findAll();
    }
    /**
     * create a method name as getUserById
     * - Ability to get user data by Id
     * */
    @Override
    public UserRegistrationData getUserById(int userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User  with id " + userId + " does not exist in database..!"));
    }
    /**
     * create a method name as loginUser
     * @param userLoginDTO - user login data (email, password)
     * */

    @Override
    public ResponseDTO loginUser(LoginDto userLoginDTO) {
        ResponseDTO dto = new ResponseDTO();
        Optional<UserRegistrationData> login = userRepo.findByEmailId(userLoginDTO.getEmail());
        if (login.isPresent()) {
            String pwd = login.get().getPassword();
            if (pwd.equals(userLoginDTO.getPassword())) {
                dto.setMessage("login successful ");
                dto.setData(login.get());
                mailService.sendEmail("kingofnorthon.rest@gmail.com", "Login Success", "LoggedIn User Details,\nUserEmail- "
                        + userRepo.findByEmailId(userLoginDTO.getEmail()));
                return dto;
            } else {
                dto.setMessage("Sorry! login is unsuccessful");
                dto.setData("Wrong password");
                return dto;
            }
        }
        return new ResponseDTO("User not found!", "Wrong email");
    }

   /* @Override
    public Object getById(String token) {
        int id= Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> getUser=userRepo.findById(id);
        if(getUser.isPresent()){
            mailService.sendEmail("kingofnorthon.rest@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +getUser.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8080/userregistration/getBy/"+token);
            return getUser;

        }
        else {
            throw new UserRegistrationException("Record for provided userId is not found");
        }*/

    /**
     * create a method name as updateUser
     * update  record data by userId
     * */

    @Override
    public UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDto userDTO) {
        UserRegistrationData userData = this.getUserById(userId);
        userData.updateUser(userDTO);
        return userRepo.save(userData);
    }
    /**
     * create a method name as deleteUser
     * delete record data by userId
     * */

    @Override
    public void deleteUser(int userId) {
        UserRegistrationData userData = this.getUserById(userId);
        userRepo.delete(userData);

    }


    /**
     * create a method name as getAllUserDataByToken
     * get all data by using token
     * */

    @Override
    public List<UserRegistrationData> getAllUserDataByToken(String token) {
        int id= Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isContactPresent=userRepo.findById(id);
        if(isContactPresent.isPresent()) {
            List<UserRegistrationData> listOfUsers=userRepo.findAll();
            mailService.sendEmail("kingofnorthon.rest@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +isContactPresent.get().getEmail()+"Please Click here to get data-> "
                    +mailService.getLink(token));
            return listOfUsers;
        }else {
            System.out.println("Exception ...Token not found!");
            return null;	}
    }



    @Override
    public String verifyUser(String token) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> user = userRepo.findById(id);

        if (user.isPresent()) {
            return user.toString();
        } else
            return null;
    }

    /**
     * create a method name as getToken
     * ability get token by particular email id
     * */

    @Override
    public String getToken(String email) {
        Optional<UserRegistrationData> userRegistration=userRepo.findByEmailId(email);
        String token=tokenUtil.createToken(userRegistration.get().getUserId());
        mailService.sendEmail("kingofnorthon.rest@gmail.com","Welcome User:  "+userRegistration.get().getFirstName(),"Token for changing password is :"+token);
        return token;
    }



}
