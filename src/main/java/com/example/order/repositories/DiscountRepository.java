package com.example.order.repositories;

import com.example.order.entities.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
    @Query(nativeQuery = true,value = "select * from discount where price=:price")
    Discount findByPrice(@Param("price") BigDecimal price);
}