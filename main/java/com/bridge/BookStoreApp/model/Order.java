package com.bridge.BookStoreApp.model;


import com.bridge.BookStoreApp.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
//import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate orderDate = LocalDate.now();
    private Integer price;
    private Integer quantity;
    private String address;
    //private int userId;
    //private int bookId;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserRegistrationData userId;

    @ManyToOne
    @JoinColumn(name="book_id")
    private BookDetails bookId;

    private boolean cancel;


    public Order(Integer price, Integer quantity, String address, BookDetails book, UserRegistrationData user) {
        this.price =  price;
        this.quantity = quantity;
        this.address = address;
        this.bookId = book;
        this.userId = user;


    }


   /* public void createOrder(OrderDto orderDto,UserRegistrationData user,BookDetails book) {
        this.orderDate = LocalDate.now();
        this.price = orderDto.getPrice();
        this.quantity = orderDto.getQuantity();
        this.address = orderDto.getAddress();
        this.userId = user;
        this.bookId = book;
        //this.cancel = false;

    }*/

}
