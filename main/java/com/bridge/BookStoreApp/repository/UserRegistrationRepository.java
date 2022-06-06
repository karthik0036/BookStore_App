package com.bridge.BookStoreApp.repository;


import com.bridge.BookStoreApp.model.UserRegistrationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Ability to provide CRUD operations and create table for given entity


public interface UserRegistrationRepository extends JpaRepository<UserRegistrationData, Integer> {
    @Query(value = "select * from userregistration where email_id= :email", nativeQuery = true)
    Optional<UserRegistrationData> findByEmailId(String email);



    //Optional<UserRegistrationData> findByEmailIdAndPassword(String , String );
}
