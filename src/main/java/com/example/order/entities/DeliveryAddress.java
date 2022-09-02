package com.example.order.entities;

import com.example.order.auditable.Auditable;

import javax.persistence.*;

@Entity
@Table
//@Builder

public class DeliveryAddress extends Auditable {
    @Id
    @GeneratedValue
    private Long addressId;

    @Column
    private String house;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String pin;

    @Column
    private String state;



    public DeliveryAddress() {
    }

    public DeliveryAddress(String house, String street, String city, String pin, String state) {
        this.house = house;
        this.street = street;
        this.city = city;
        this.pin = pin;
        this.state = state;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}