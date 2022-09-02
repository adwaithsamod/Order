package com.example.order.repositories;

import com.example.order.entities.JwtToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends CrudRepository<JwtToken,Long> {
    JwtToken findByUsername(String username);
}
