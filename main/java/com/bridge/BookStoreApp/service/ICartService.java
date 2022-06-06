package com.bridge.BookStoreApp.service;

import com.bridge.BookStoreApp.dto.CartDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.model.Cart;

import java.util.Optional;

/**
 * Created ICartService interface to achieve abstraction
 */

public interface ICartService {

    ResponseDTO getCartDetails();

    Optional<Cart> getCartDetailsById(Integer cartId);

    Optional<Cart> deleteCartItemById(Integer cartId);

    Cart updateRecordById(Integer cartId, CartDto cartDto);

    Cart insertItems(String token,CartDto cartdto);




}
