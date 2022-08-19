package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.entities.ItemOrder;
import com.example.order.services.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemOrderController {

    @Autowired
    private ItemOrderService itemOrderService;

    @RequestMapping("/orders")
    public Response getAllOrders(){
        return itemOrderService.getAllOrders();
    }

    @RequestMapping("orders/{orderId}")
    public Response getItemOrderById(@PathVariable Long orderId){
        ItemOrder order = itemOrderService.getItemOrderById(orderId);
        return new Response(true,"Fetched order",order);

    }










}
