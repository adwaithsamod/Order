package com.example.order.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private ArrayList<ProductCreateRequest> products;

    private String voucherName;

    private Long addressId;


}
