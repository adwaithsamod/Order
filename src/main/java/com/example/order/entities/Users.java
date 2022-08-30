package com.example.order.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
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
    private List<DeliveryAddress> deliveryAddresses;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nationality;

    private String userName;
    private String password;

    private String authorities;
    private boolean enabled;




    public Users() {

    }

    public Users(String name, String phoneNumber, String email, List<DeliveryAddress> deliveryAddresses, Integer age, Gender gender, String nationality, String userName, String password, String authorities, boolean enabled) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddresses = deliveryAddresses;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }


    public Users(Users user) {
        super();
    }
}
