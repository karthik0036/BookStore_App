package com.bridge.BookStoreApp.service;

import com.bridge.BookStoreApp.dto.BookDetailsDto;
import com.bridge.BookStoreApp.exception.UserRegistrationException;
import com.bridge.BookStoreApp.model.BookDetails;
import com.bridge.BookStoreApp.model.UserRegistrationData;
import com.bridge.BookStoreApp.repository.BookDetailsRepository;
import com.bridge.BookStoreApp.repository.UserRegistrationRepository;
import com.bridge.BookStoreApp.util.Email;
import com.bridge.BookStoreApp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridge.BookStoreApp.util.Email;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Created BookService class to serve api calls done by controller layer
 */
public class BookDetailsService implements IBookDetailsService {
    /**
     * Autowired BookStoreRepository interface to inject its dependency here
     */
    @Autowired
    BookDetailsRepository bookRepo;

    @Autowired
    UserRegistrationRepository userRepo;

    @Autowired
    UserRegistrationService service;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    Email mailService;

    /**
     * create a method name as showAllBooks
     * - Ability to get all book' data by findAll() method
     */
    @Override
    public List<BookDetails> showAllBooks() {
        List<BookDetails> allBooks = bookRepo.findAll();
        return allBooks;

    }
    /* create a method name as addBook
       Ability to save book details to repository
       */
    @Override
    public BookDetails addBook(String token,BookDetailsDto bookDto) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isPresent = userRepo.findById(id);
        if (isPresent.isPresent()) {
            BookDetails bookAdded = new BookDetails(bookDto);
            bookRepo.save(bookAdded);
            mailService.sendEmail("kingofnorthon.rest@gmail.com", "Book Details", "Added SuccessFully,\nBook Name: "
                    + bookDto.getBookName()+ "\nBook Author - " + bookDto.getBookAuthor() + "\nBook Price - " + bookDto.getBookPrice() +
                    "\nQuantity - "+ bookDto.getBookQuantity());
            return bookAdded;
        }
        return null;
    }
    /**
     * create a method name as getBookById
     * - Ability to get book data by id
     */
    @Override
    public BookDetails getBookById(int bookId) {
            return bookRepo.findById(bookId)
                    .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exist in database..!"));
    }
    /**
     * create a method name as getBookByName
     * ability to get data by particular book Name
     * */
    @Override
    public List<BookDetails> getBookByName(String bookName) {
        return bookRepo.findByBookName(bookName);
    }
    /**
     * create a method name as updateBook
     * Ability to update book data for particular id
     * */
    @Override
    public BookDetails updateBook(int bookId, BookDetailsDto bookDTO) {
            BookDetails bookData = this.getBookById(bookId);
            bookData.updateBook(bookDTO);
            return bookRepo.save(bookData);
    }

    /**
     * create a method name as deleteBook
     * ability to delete data by particular book id
     * */
    @Override
    public void deleteBook( int bookId) {
            BookDetails isBookPresent = this.getBookById( bookId);
            bookRepo.delete(isBookPresent);
    }
    /**
     * create a method name as updateBookQuantity
     * ability to update data by particular book id
     * */
    @Override
    public BookDetails updateBookQuantity (int bookId, int bookQuantity){
            BookDetails book = this.getBookById(bookId);
            book.setBookQuantity(bookQuantity);
            return bookRepo.save(book);
    }

    //list of book in descending order
    @Override
    public List<BookDetails> sortBooksDesc() {

        return bookRepo.sortBooksByDesc();
    }
    //list of book in ascending order
    @Override
    public List<BookDetails> sortBooksAsc() {

        return bookRepo.sortBooksByAsc();
    }









}