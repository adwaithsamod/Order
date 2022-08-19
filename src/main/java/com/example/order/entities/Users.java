package com.example.order.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user_details")
public class Users extends Auditable{
    @Id
    @GeneratedValue
    private Long id;


    private String name;



    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_delivery_address")
    private List<DeliveryAddress> deliveryAddress;


    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    private String nationality;

    private String userName;
    private String password;
    private boolean enabled;




    public Users() {

    }

    public Users(String name, String phoneNumber, String email, List<DeliveryAddress> deliveryAddress, Integer age, Gender gender, String nationality) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
    }

    public Users(String name, String phoneNumber, String email, Integer age, Gender gender, String nationality, String userName, String password, boolean enabled) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }
}
