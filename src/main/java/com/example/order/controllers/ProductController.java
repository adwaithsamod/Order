package com.example.order.controllers;


import com.example.order.responseModel.Response;
import com.example.order.entities.Product;
import com.example.order.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @RequestMapping(method=RequestMethod.POST, value="products")
    public Response addProducts(@RequestBody List<Product> products){
       return productService.addProducts(products);
    }

    @GetMapping(value="hello")
    public String hello(){
        return "Hello";
    }


    @RequestMapping("/products")
    public Response getAllProducts(){
       return productService.getAllProducts();
    }

    @RequestMapping("users/{userId}/products/{productId}/price")
    public Response getPrice(@PathVariable Long productId){
        return productService.getPrice(productId);
    }

    @RequestMapping("users/{userId}/products/{productId}/stock")
    public Response getStock(@PathVariable Long productId){
        return productService.getStock(productId);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="products/{id}")
    public Response deleteInventory(@PathVariable Long id){
        return productService.deleteInventory(id);
    }



}
