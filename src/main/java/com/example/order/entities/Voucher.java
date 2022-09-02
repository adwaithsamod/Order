package com.example.order.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Voucher{
    @Id
    @GeneratedValue
    private Long voucherId;

    @Column(unique = true)
    private String voucherName;

    private BigDecimal discountAmt;

    private Calendar createdDate;

    private Calendar expiryDate;

    private Long userCount;

    public Voucher(String voucherName, BigDecimal discountAmt, Calendar createdDate, Calendar expiryDate, Long userCount) {
        this.voucherName = voucherName;
        this.discountAmt = discountAmt;
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
        this.userCount = userCount;
    }
}