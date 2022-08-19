package com.example.order.services;

import com.example.order.controllers.request.AddressCreateRequest;
import com.example.order.entities.DeliveryAddress;
import com.example.order.repositories.DeliveryAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressService {
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private UsersService usersService;

    public DeliveryAddress getAddressById(Long deliveryAddressId){
            return deliveryAddressRepository.findById(deliveryAddressId).get();
        }


    public DeliveryAddress addDeliveryAddress(AddressCreateRequest addressCreateRequest, Long userId) {
        DeliveryAddress deliveryAddress = new DeliveryAddress(
                addressCreateRequest.getHouse(),
                addressCreateRequest.getStreet(),
                addressCreateRequest.getCity(),
                addressCreateRequest.getPin(),
                addressCreateRequest.getState()
        );
        deliveryAddressRepository.save(deliveryAddress);
        usersService.addDeliveryAddressId(userId, deliveryAddress);

        return deliveryAddress;

    }
}
