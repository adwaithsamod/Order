package com.example.order.entities;


import com.example.order.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="user_details")
public class Users extends Auditable {
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

    @Column(unique = true)
    private String userName;

    @JsonIgnore
    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;


    public Users() {

    }

    public Users(String name, String phoneNumber, String email, List<DeliveryAddress> deliveryAddresses, Integer age, Gender gender, String nationality, String userName, String password, boolean enabled) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddresses = deliveryAddresses;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.userName = userName;
        this.password = password;

        this.enabled = enabled;
    }


    public Users(Users user) {
        super();
    }

//    public Users(String userName, String password, Set<SimpleGrantedAuthority> authority) {
//        this.userName = userName;
//        this.password = password;
//        this.roles = authority;
//    }
}