package com.bridge.BookStoreApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
//import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    //@OneToOne//
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserRegistrationData user;

    //@OneToOne
    @OneToOne
    @JoinColumn(name = "book_id")
    private BookDetails book;

    @Column(name = "quantity")
    private Integer quantity;


    public Cart(Integer quantity, BookDetails book, UserRegistrationData user) {
        super();
        this.quantity = quantity;
        this.book = book;
        this.user = user;
    }

    public Cart() {

    }
}
