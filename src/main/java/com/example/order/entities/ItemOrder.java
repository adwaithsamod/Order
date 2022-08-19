package com.example.order.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class ItemOrder extends Auditable {
    @Id
    @GeneratedValue
    private Long orderId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order_master_id")
    private OrderMaster orderMaster;

    @Column
    private Long productId;

    @Column
    private Long quantity;

    @Column
    private BigDecimal price;

    @Column
    private String deliveryStatus;

    @Column
    private BigDecimal discount;


    public ItemOrder() {
    }

    public ItemOrder( OrderMaster orderMaster, Long productId, Long quantity, BigDecimal price, String deliveryStatus, BigDecimal discount) {

        this.orderMaster = orderMaster;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.deliveryStatus = deliveryStatus;
        this.discount = discount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public Long getQuantity() {
        return quantity;
    }

    public OrderMaster getOrderMaster() {
        return orderMaster;
    }

    public void setOrderMaster(OrderMaster orderMaster) {
        this.orderMaster = orderMaster;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }


}
