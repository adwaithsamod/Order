package com.example.order.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Discount extends Auditable{
    @Id
    @GeneratedValue
    private Long discountId;
    @Column(unique = true)
    private BigDecimal price;
    private BigDecimal availableDiscount;
}
