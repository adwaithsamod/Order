package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.VoucherCreateRequest;
import com.example.order.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @RequestMapping(method = RequestMethod.POST, value = "vouchers")
    public Response addVoucher(@RequestBody List<VoucherCreateRequest> voucherCreateRequestList){
        return voucherService.addVoucher(voucherCreateRequestList);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable Long voucherId){
        return voucherService.deleteVoucher(voucherId);
    }

    @RequestMapping(value = "vouchers/{voucherName}")
    public BigDecimal getVoucherByName(@PathVariable String voucherName){
        return voucherService.getVoucherByName(voucherName);
    }

}
