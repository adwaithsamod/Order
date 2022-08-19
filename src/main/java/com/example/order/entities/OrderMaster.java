package com.example.order.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
public class OrderMaster extends Auditable{
    @Id
    @GeneratedValue
    private Long orderMasterId;

    @Column
    private Date date;

    @Column
    private BigDecimal totalPrice;

    @Column
    private BigDecimal totalDiscount;

    @Column
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private Users users;

    public OrderMaster() {
    }

    public OrderMaster( Date date, BigDecimal totalPrice, BigDecimal totalDiscount, String status, Users users) {

        this.date = date;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.users = users;
    }


}
