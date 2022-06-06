package com.bridge.BookStoreApp.controller;


import com.bridge.BookStoreApp.dto.BookDetailsDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.model.BookDetails;
import com.bridge.BookStoreApp.service.IBookDetailsService;
import com.bridge.BookStoreApp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * In Controller class we write the API's here
 */

@RestController
@RequestMapping("/bookservice")
@Slf4j
public class BookDetailsController {

    //* Autowired IBookService to inject its dependency here
    @Autowired
    IBookDetailsService bookService;

    @Autowired
    private TokenUtil tokenUtil;

    // Ability to call api to retrieve all book records
    //Get All
    @GetMapping(value = {"", "/", "/getBooks"})
    public ResponseEntity<ResponseDTO> getAllBooks() {
        List<BookDetails> allBooks = bookService.showAllBooks();
        ResponseDTO dto = new ResponseDTO("All Books Retrieved successfully:", allBooks);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Ability to call api to retrieve all book records By Names
    @GetMapping("/getBookByName/{bookName}")
    public ResponseEntity<ResponseDTO> getOneBookByName(@PathVariable String bookName)
    {
        List<BookDetails> getOneBook = bookService.getBookByName(bookName);
        ResponseDTO dto = new ResponseDTO("Book retrieved successfully"+bookName, getOneBook);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // Ability to call api to retrieve all book records By Id
    @GetMapping("/getBook/{bookId}")
    public ResponseEntity<ResponseDTO> getOneBook(@PathVariable int bookId)
    {
        BookDetails getOneBook = bookService.getBookById(bookId);
        ResponseDTO dto = new ResponseDTO("Book retrieved successfully"+bookId, getOneBook);
        return new ResponseEntity<ResponseDTO>(dto,HttpStatus.OK);
    }

    // Ability to call api to insert Book record
    // Add Data to repo
    @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> addBook(@RequestHeader(name = "token") String token,@RequestBody BookDetailsDto bookDto) {
        BookDetails addBook = bookService.addBook(token,bookDto);
        ResponseDTO dto = new ResponseDTO("Book added successfully ",tokenUtil.createToken(addBook.getBookId()));
        return new ResponseEntity<ResponseDTO>(dto, HttpStatus.CREATED);
    }

    // Ability to call api to update book record by BookId
    //update record by id
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBookData(@PathVariable("bookId") int bookId,
                                                      @RequestBody BookDetailsDto bookDTO) {
        BookDetails updateBook = bookService.updateBook(bookId, bookDTO);
        log.debug(" After Update " + updateBook.toString());
        ResponseDTO response = new ResponseDTO("Updated  for" + bookId, updateBook);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }
    // Ability to call api to delete book record by BookId
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + bookId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    @PutMapping("/updatequantity/{bookId}/{bookQuantity}")
    public ResponseEntity<ResponseDTO> updateBookQuantity(@PathVariable int bookId, @PathVariable int bookQuantity) {
        BookDetails updateBookQuantity = bookService.updateBookQuantity(bookId, bookQuantity);
        ResponseDTO response = new ResponseDTO("Book Quantity Update is success for id " + bookId, updateBookQuantity);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    // to sort book records in ascending order
    @GetMapping("/sortasc")
    public ResponseEntity<ResponseDTO> sortBooksAsc() {
        List<BookDetails> book = null;
        book = bookService.sortBooksAsc();
        ResponseDTO dto = new ResponseDTO("Books in ascending order :", book);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    //to sort book records in descending order
    @GetMapping("/sortdesc")
    public ResponseEntity<ResponseDTO> sortBooksDesc() {
        List<BookDetails> book = null;
        book = bookService.sortBooksDesc();
        ResponseDTO dto = new ResponseDTO("Books in  descending order :", book);
        return new ResponseEntity(dto, HttpStatus.OK);
    }


}
