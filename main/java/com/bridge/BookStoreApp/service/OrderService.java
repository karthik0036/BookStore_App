package com.bridge.BookStoreApp.service;


import com.bridge.BookStoreApp.dto.OrderDto;
import com.bridge.BookStoreApp.exception.UserRegistrationException;
import com.bridge.BookStoreApp.model.BookDetails;
import com.bridge.BookStoreApp.model.Order;
import com.bridge.BookStoreApp.model.UserRegistrationData;
import com.bridge.BookStoreApp.repository.BookDetailsRepository;
import com.bridge.BookStoreApp.repository.OrderRepository;
import com.bridge.BookStoreApp.repository.UserRegistrationRepository;
import com.bridge.BookStoreApp.util.Email;
import com.bridge.BookStoreApp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private BookDetailsRepository bookRepo;
    @Autowired
    private UserRegistrationRepository userRepo;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    Email mailService;



    @Override
    public String insertOrder(OrderDto orderdto) {
        ArrayList<BookDetails> bookList = new ArrayList<>();
        Optional<BookDetails> book = bookRepo.findById(orderdto.getBookId());
        Optional<UserRegistrationData> user = userRepo.findById(orderdto.getUserId());
        if (book.isPresent() && user.isPresent()) {
                int totalPrice = book.get().getBookPrice() * orderdto.getQuantity();
                List<String> nameList = bookList.stream().map(BookDetails::getBookName).toList();
                Order newOrder = new Order(totalPrice, orderdto.getQuantity(), orderdto.getAddress(), book.get(), user.get());
                orderRepo.save(newOrder);
                log.info("Order record inserted successfully");
                String token = tokenUtil.createToken(newOrder.getId());
                mailService.sendEmail("kingofnorthon.rest@gmail.com", "Order Details", "Order Confirmed,\nOrderId- "
                    + newOrder.getId() + "\nOrder placed on - " + orderdto.getOrderDate() + " for bookId - " + orderdto.getBookId() +".\nShipping to address - "+ orderdto.getAddress()
                        +  ".\nTotal price need to pay " + totalPrice +"\nBook Names - " + nameList);
            log.info("Order record inserted successfully");
            return token;
            //return newOrder;
            } else {
                throw new UserRegistrationException("Requested quantity is out of stock");
            }
        } /*else {
            throw new UserRegistrationException("Book or User doesn't exists");
        }*/

    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }

    public Order getOrderById(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new UserRegistrationException("Order Record doesn't exists");
        } else {
            log.info("Order record retrieved successfully for id " + id);
            return order.get();
        }
    }


    @Override
    public String cancelOrder(String token,int orderId) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isPresent = userRepo.findById(id);
        if (isPresent.isPresent()) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isPresent()) {
            order.get().setCancel(true);
            orderRepo.save(order.get());
            mailService.sendEmail("kingofnorthon.rest@gmail.com", "Order Cancellation", "Order Cancelled,\nOrderId- "
                    + orderId);
            return "Cancel order Successful";
        }
        throw new UserRegistrationException("OrderId  doesn't exists");
        }
        return  "cancel order not successful";
    }


}
