package com.bridge.BookStoreApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class OrderDto {
    public Integer  price;
    public Integer  quantity;
    public String address;
    public Integer  userId;
    public Integer  bookId;
    public LocalDate orderDate = LocalDate.now();
    //private boolean cancel;
}
