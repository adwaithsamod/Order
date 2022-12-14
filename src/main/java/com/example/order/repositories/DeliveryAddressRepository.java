package com.example.order.repositories;

import com.example.order.entities.DeliveryAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Long> {
}