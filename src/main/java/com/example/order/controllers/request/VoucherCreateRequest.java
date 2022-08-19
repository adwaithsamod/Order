package com.example.order.controllers.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class VoucherCreateRequest {

    private String voucherName;

    private BigDecimal discountAmt;

    private Calendar date;

    private Calendar expiryDate;

    private Long userCount;

    public VoucherCreateRequest(String voucherName, BigDecimal discountAmt, Calendar expiryDate, Long userCount) {
        this.voucherName = voucherName;
        this.discountAmt = discountAmt;
        this.date = Calendar.getInstance();
        this.expiryDate = expiryDate;
        this.userCount = userCount;
    }
}


