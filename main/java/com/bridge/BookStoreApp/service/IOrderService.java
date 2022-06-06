package com.bridge.BookStoreApp.service;

import com.bridge.BookStoreApp.dto.OrderDto;
import com.bridge.BookStoreApp.model.Order;

import java.util.List;

public interface IOrderService {
    public String insertOrder(OrderDto orderdto);

    public List<Order> getAllOrders();

    public Order getOrderById(Integer id);

    //public Order updateOrderRecord(Integer id, OrderDto dto);

    //public Order deleteOrderRecord(Integer id);

    //String cancelOrder(Integer id);
    String cancelOrder( String token,int orderId);
}
