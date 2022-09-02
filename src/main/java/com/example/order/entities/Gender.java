package com.example.order.entities;

public enum Gender {
    male("male"),
    female("female"),
    other("other");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}