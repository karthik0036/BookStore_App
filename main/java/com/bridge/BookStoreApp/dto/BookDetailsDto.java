package com.bridge.BookStoreApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsDto {
    public String bookName;
    public String bookAuthor;
    public String bookDescription;
    public String bookLogo;
    public Integer bookPrice;
    public Integer bookQuantity;
}
