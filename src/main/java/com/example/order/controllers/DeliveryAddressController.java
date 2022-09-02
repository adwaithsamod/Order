
package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.AddressCreateRequest;
import com.example.order.entities.DeliveryAddress;
import com.example.order.services.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @RolesAllowed("USER")
    @RequestMapping(method = RequestMethod.POST, value = "users/{userId}/address")
    public Response addDeliveryAddress(@RequestBody AddressCreateRequest addressCreateRequest, @PathVariable Long userId){
        DeliveryAddress deliveryAddress = deliveryAddressService.addDeliveryAddress(addressCreateRequest,userId);
        return new Response(true,"Address added",deliveryAddress);
    }
}