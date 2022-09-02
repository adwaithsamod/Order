package com.example.order.controllers.request;

import java.math.BigDecimal;

public class WalletTopUpRequest {
    private Long userId;
    private BigDecimal money;

    public WalletTopUpRequest() {
    }

    public WalletTopUpRequest(Long userId, BigDecimal money) {
        this.userId = userId;
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}