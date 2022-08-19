package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.OrderCreateRequest;
import com.example.order.entities.ItemOrder;
import com.example.order.services.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderMasterController {
    @Autowired
    private OrderMasterService orderMasterService;

    @RequestMapping("users/{userId}/orders")
    public Response getAllOrdersByUser(@PathVariable Long userId) {
        List<ItemOrder> itemOrderList = orderMasterService.getAllOrdersByUser(userId);
        return new Response(true, "Orders Placed by user", itemOrderList);
    }

    @RequestMapping(method = RequestMethod.POST, value="users/{userId}/buy")
    public Response buy(@RequestBody OrderCreateRequest orderCreateRequest, @PathVariable Long userId){
        return orderMasterService.buy(orderCreateRequest.getProducts(), orderCreateRequest.getVoucherName(), orderCreateRequest.getAddressId(), userId);
//        return new Response(true,"Order Completed",messages);
//        String msg = itemOrderService.buy(orderCreateRequest.getProducts(), orderCreateRequest.getDiscount(), userId);

    }

    @RequestMapping(method =  RequestMethod.PATCH,value = "orders/{orderId}/status")
    public Response updateStatus(@RequestBody String status, @PathVariable Long orderId){
        Response response = orderMasterService.updateStatus(orderId,status);
        return response;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "orders/status/update")
    public Response bulkStatusUpdate(@RequestBody List<Long> idList){
        Response response = orderMasterService.bulkStatusUpdate(idList);
        return response;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "users/{userId}/orders/{orderId}/cancel")
    public Response cancelOrder(@RequestBody Long orderId){
        Response response = orderMasterService.cancelOrder(orderId);
        return response;
    }
}