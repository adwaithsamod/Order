package com.example.order.repositories;

import com.example.order.entities.DeliveryAddress;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Long> {
}
