package com.example.order.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateRequest {

    private String house;


    private String street;


    private String city;


    private String pin;

    private String State;
}
