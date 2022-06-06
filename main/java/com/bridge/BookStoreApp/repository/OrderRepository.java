package com.bridge.BookStoreApp.repository;


import com.bridge.BookStoreApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends JpaRepository<Order,Integer> {
}
