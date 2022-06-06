package com.bridge.BookStoreApp.service;


import com.bridge.BookStoreApp.dto.CartDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.exception.UserRegistrationException;
import com.bridge.BookStoreApp.model.BookDetails;
import com.bridge.BookStoreApp.model.Cart;
import com.bridge.BookStoreApp.model.UserRegistrationData;
import com.bridge.BookStoreApp.repository.BookDetailsRepository;
import com.bridge.BookStoreApp.repository.CartRepository;
import com.bridge.BookStoreApp.repository.UserRegistrationRepository;
import com.bridge.BookStoreApp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Created CartService class to serve api calls done by controller layer
 */
public class CartService implements ICartService {
    /**
     * Autowired interfaces to inject its dependency here
     */
    @Autowired
    BookDetailsRepository bookStoreRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    CartRepository bookStoreCartRepository;


    @Autowired
    TokenUtil tokenUtil;

    /**
     * create a method name as insertItems
     * Ability to save cart details to repository
     * */

    @Override
    public Cart insertItems(String token ,CartDto cartdto) {
        Optional<BookDetails> book = bookStoreRepository.findById(cartdto.getBookId());
        Optional<UserRegistrationData> userRegistration = userRegistrationRepository.findById(cartdto.getUserId());
        if (book.isPresent() && userRegistration.isPresent()) {
            Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get());
            bookStoreCartRepository.save(newCart);
            return newCart;
        } else {
            throw new UserRegistrationException("Book or User does not exists");
        }
    }
    /**
     * create a method name as getCartDetails
     * - Ability to get all cart' data by findAll() method
     * */
    @Override
    public ResponseDTO getCartDetails() {
        List<Cart> getCartDetails=bookStoreCartRepository.findAll();
        ResponseDTO dto= new ResponseDTO();
        if (getCartDetails.isEmpty()){
            String   message=" Not found Any Cart details ";
            dto.setMessage(message);
            dto.setData(0);
            return dto;

        }
        else {
            dto.setMessage("the list of cart items is sucussfully retrived");
            dto.setData(getCartDetails);
            return dto;
        }
    }
    /**
     * create a method name as getCartDetailsById
     * - Ability to get cart data by cartId*/
    @Override
    public Optional<Cart> getCartDetailsById(Integer cartId) {
        Optional<Cart> getCartData=bookStoreCartRepository.findById(cartId);
        if (getCartData.isPresent()){
            return getCartData;
        }
        else {
            throw new UserRegistrationException(" Didn't find any record for this particular cartId");
        }
    }
    /**
     * create a method name as deleteCartItemById
     * ability to delete data by particular cart id
     * */
    @Override
    public Optional<Cart> deleteCartItemById(Integer cartId) {
        Optional<Cart> deleteData=bookStoreCartRepository.findById(cartId);
        if (deleteData.isPresent()){
            bookStoreCartRepository.deleteById(cartId);
            return deleteData;
        }
        else {
            throw new UserRegistrationException(" Did not get any cart for specific cart id ");
        }

    }
    /**
     * create a method name as updateRecordById
     * Ability to update cart data for particular id
     * */
    @Override
    public Cart updateRecordById(Integer cartId, CartDto cartDTO) {
        Optional<Cart> cart = bookStoreCartRepository.findById(cartId);
        Optional<BookDetails>  book = bookStoreRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if(cart.isPresent()) {
            if(book.isPresent() && user.isPresent()) {
                Cart newCart = new Cart(cartDTO.getQuantity(),book.get(),user.get());
                bookStoreCartRepository.save(newCart);
                log.info("Cart record updated successfully for id "+cartId);
                return newCart;
            }
            else {
                throw new UserRegistrationException("Book or User doesn't exists");
            }
        }
        else {
            throw new UserRegistrationException(("Cart Record doesn't exists"));
        }

    }

}
