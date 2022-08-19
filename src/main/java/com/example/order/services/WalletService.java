package com.example.order.services;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.WalletTopUpRequest;
import com.example.order.entities.Users;
import com.example.order.entities.Wallet;
import com.example.order.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;


    public List<Wallet> getAllWallet() {
        List<Wallet> wallets = new ArrayList<>();
        walletRepository.findAll().forEach(wallets::add);
        return wallets;
    }

    public BigDecimal getBalance(Long userId) {
        Optional<Wallet> wallet = walletRepository.findByUserId(userId);
        return wallet.get().getWalletBalance();
    }

    public void decrementWallet(Long walletId, BigDecimal price, BigDecimal walletBalance) {
        Wallet wallet = walletRepository.findById(walletId).get();
        wallet.setWalletBalance(walletBalance.subtract(price));
        walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId).get();
    }

    public Response topUp(WalletTopUpRequest walletTopupRequest) {
            Wallet wallet = getWalletByUserId(walletTopupRequest.getUserId());
            wallet.setWalletBalance(getBalance(walletTopupRequest.getUserId()).add(walletTopupRequest.getMoney()));
            walletRepository.save(wallet);
            return new Response(true, "Wallet fetched", wallet);
    }

    public void createWallet(Users users) {
        Wallet wallet = new Wallet(BigDecimal.valueOf(0), users);
        walletRepository.save(wallet);
    }
}
