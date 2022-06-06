package com.bridge.BookStoreApp.controller;

import com.bridge.BookStoreApp.dto.OrderDto;
import com.bridge.BookStoreApp.dto.ResponseDTO;
import com.bridge.BookStoreApp.model.Order;
import com.bridge.BookStoreApp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController  {

    @Autowired
    IOrderService orderService;

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertOrder(@RequestBody OrderDto orderdto){
        String newOrder = orderService.insertOrder(orderdto);
        ResponseDTO dto = new ResponseDTO("Order place successfully !",newOrder);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @GetMapping("/retrieveAllOrders")
    public ResponseEntity<ResponseDTO> getAllOrderRecords(){
        List<Order> newOrder = orderService.getAllOrders();
        ResponseDTO dto = new ResponseDTO("All records retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    @GetMapping("/retrieveOrder/{id}")
    public ResponseEntity<ResponseDTO> getBookRecordById(@PathVariable Integer id){
        Order newOrder = orderService.getOrderById(id);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }



    @PutMapping("/cancelOrder/{id}")
    public ResponseEntity<ResponseDTO> cancelOrder(@RequestHeader(name = "token") String token,@PathVariable int id) {
        String order=orderService.cancelOrder(token,id);
        ResponseDTO response= new ResponseDTO("id "+id,  order);
        return new ResponseEntity<> (response,HttpStatus.ACCEPTED);
    }





}
