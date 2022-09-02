
package com.example.order.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.order.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {


}