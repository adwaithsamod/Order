package com.example.order.services;

import com.example.order.responseModel.Response;
import com.example.order.entities.Discount;
import com.example.order.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public Response addDiscounts(List<Discount> discounts) {
        for(Discount discount : discounts){
            try {
                discountRepository.save(discount);
            }catch(DataIntegrityViolationException e){
                Discount existingDiscount = discountRepository.findByPrice(discount.getPrice());
                discountRepository.delete(existingDiscount);
                discountRepository.save(discount);
            }

        }
        return new Response(true,"Discounts Added",discounts);
    }

    public BigDecimal getAvailableDiscount(BigDecimal price) {
        List<BigDecimal> availableDiscounts = new ArrayList<>();
        List<Discount> discounts = new ArrayList<>();
        discountRepository.findAll().forEach(discounts::add);
        for(Discount discount : discounts){
            if(price.compareTo(discount.getPrice())>=0){
                availableDiscounts.add(discount.getAvailableDiscount());
            }
        }
        try {
            return Collections.max(availableDiscounts);
        }catch(NoSuchElementException e){
            return BigDecimal.valueOf(0);
        }
    }
}