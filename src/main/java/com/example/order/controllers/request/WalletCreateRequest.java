package com.example.order.controllers.request;

import java.math.BigDecimal;

public class WalletCreateRequest {

    private BigDecimal walletBalance;
    private Long userId;

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
