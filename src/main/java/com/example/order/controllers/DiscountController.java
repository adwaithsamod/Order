package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.entities.Discount;
import com.example.order.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @RequestMapping(method = RequestMethod.POST, value = "discounts")
    public Response addDiscounts(@RequestBody List<Discount> discounts){
       return discountService.addDiscounts(discounts);
    }

    @RequestMapping(value="discounts/{price}")
    public Response getAvailableDiscount(@PathVariable BigDecimal price){

        BigDecimal availableDiscount = discountService.getAvailableDiscount(price);
        return new Response(true,"Available discount for this price",availableDiscount);
    }

}
