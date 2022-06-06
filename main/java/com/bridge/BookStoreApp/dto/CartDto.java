package com.bridge.BookStoreApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDto {
    public int userId;
    public  int bookId;
    @NotNull(message="Book quantity to be provided")
    public  Integer quantity;
}
