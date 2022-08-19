package com.example.order.controllers;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.WalletTopUpRequest;
import com.example.order.entities.Wallet;
import com.example.order.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

//    @RequestMapping(method = RequestMethod.POST,value = "wallet")
//    public void addWallet(@RequestBody WalletCreateRequest walletCreateRequest){
//        walletService.addWallet(walletCreateRequest);
//    }

    @RequestMapping("/wallets")
    public List<Wallet> getAllWallet(){
        return walletService.getAllWallet();
    }

    @RequestMapping("users/{userId}/wallet")
    public BigDecimal getBalance(@PathVariable Long userId){
        return walletService.getBalance(userId);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "users/{userId}/wallet/topUp")
    public Response topUpWallet(@RequestBody WalletTopUpRequest walletTopUpRequest){
        Response response = walletService.topUp(walletTopUpRequest);
        return response;
    }


}
