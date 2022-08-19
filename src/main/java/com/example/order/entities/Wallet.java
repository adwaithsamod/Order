package com.example.order.entities;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table
public class Wallet extends Auditable{
    @Id
    @GeneratedValue
    private Long walletId;
    
    @Column(name="wallet_balance")
    private BigDecimal walletBalance;

    @OneToOne(cascade = CascadeType.ALL) //default name
    @JoinColumn(name = "fk_user_id")
    private Users users;



    public Wallet() {
    }

    public Wallet(BigDecimal walletBalance, Users users) {

        this.walletBalance = walletBalance;
        this.users = users;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }
}
