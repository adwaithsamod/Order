package com.example.order.services;

import com.example.order.responseModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.order.entities.Product;
import com.example.order.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Response addProducts(List<Product> products) {

        productRepository.saveAll(products);
        return new Response(true,"Products Added",products);
    }

    public Response getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        Response response = new Response(true, "Fetched Products",products);
        return response;
    }

    public Response getPrice(Long productId) {

            BigDecimal price = productRepository.findById(productId).get().getPrice();
            Response response = new Response(true,"Price fetched",price);
            return response;


    }

    public Response getStock(Long productId) {


            Long stock = productRepository.findById(productId).get().getStock();
            Response response = new Response(true,"Stock fetched",stock);
             return response;

    }

    public void decrementStock(Long productId, Long quantity) {
        Product product = productRepository.findById(productId).get();
        product.setStock(product.getStock()-quantity);
        productRepository.save(product);
    }

    public Response deleteInventory(Long id) {
            Response response = new Response();
            response.setResult(productRepository.findById(id));
            productRepository.deleteById(id);
            response.setMessage("Deleted");
            response.setIsValid(true);
            return response;
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }
}
