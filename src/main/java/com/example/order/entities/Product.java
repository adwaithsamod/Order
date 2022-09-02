package com.example.order.entities;


import com.example.order.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable {
    @Id
    @GeneratedValue
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "stock")
    private  Long stock;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "Description")
    private String description;

    @Column
    private BigDecimal discount;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private String retailer;



}