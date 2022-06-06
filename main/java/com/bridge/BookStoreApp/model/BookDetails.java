package com.bridge.BookStoreApp.model;

//import javax.persistence.*;
import jakarta.persistence.*;


import com.bridge.BookStoreApp.dto.BookDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Column( name = "book_name")
    private String bookName;

    @Column  (name = "book_author")
    private String bookAuthor;

    @Column (name = "book_description")
    private String bookDescription;

    @Column (name = "book_logo")
    private String bookLogo;

    @Column (name = "book_price")
    private Integer bookPrice;

    @Column (name = "book_quantity")
    private Integer bookQuantity;



    public  BookDetails (BookDetailsDto bookDto){
        this.updateBook(bookDto);

    }


    public void updateBook (BookDetailsDto bookDto){
        this.bookName = bookDto.getBookName();
        this.bookAuthor = bookDto.getBookAuthor();
        this.bookDescription =  bookDto.getBookDescription();
        this.bookLogo =  bookDto.getBookLogo();
        this.bookPrice = bookDto.getBookPrice();
        this.bookQuantity = bookDto.getBookQuantity();

    }

}
