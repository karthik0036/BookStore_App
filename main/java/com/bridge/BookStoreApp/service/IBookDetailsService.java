package com.bridge.BookStoreApp.service;

import com.bridge.BookStoreApp.dto.BookDetailsDto;
import com.bridge.BookStoreApp.model.BookDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created IBookService interface to achieve abstraction
 */
public interface IBookDetailsService {

    List<BookDetails> showAllBooks();

    BookDetails addBook(String token,BookDetailsDto bookDto);

    BookDetails getBookById(int bookId);

    List<BookDetails> getBookByName(String bookName);

    BookDetails updateBook(int bookId, BookDetailsDto bookDTO);

     void deleteBook(int bookId);

     BookDetails updateBookQuantity(int bookId, int bookQuantity);

     //list of book in ascending order
    List<BookDetails> sortBooksAsc();

    //list of book in descending order
    List<BookDetails> sortBooksDesc();

}
