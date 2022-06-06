package com.bridge.BookStoreApp.repository;

import com.bridge.BookStoreApp.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailsRepository  extends JpaRepository<BookDetails, Integer> {


    //Optional<BookDetails> findByBookId(int bookId);

    @Query(value = "SELECT * from books where book_name = :bookName", nativeQuery = true)
    List<BookDetails> findByBookName(String bookName);

    @Query(value = "SELECT * from books ORDER BY book_price", nativeQuery = true)
     List<BookDetails> sortBooksByAsc();

    @Query(value = "SELECT * from books ORDER BY book_price DESC", nativeQuery = true)
     List<BookDetails> sortBooksByDesc();


}
