package com.example.order.services;

import com.example.order.responseModel.Response;
import com.example.order.controllers.request.VoucherCreateRequest;
import com.example.order.entities.Voucher;
import com.example.order.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;
    public Response addVoucher(List<VoucherCreateRequest> voucherCreateRequestList) {
        Voucher voucher ;
        List<Voucher> voucherList = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (VoucherCreateRequest voucherCreateRequest : voucherCreateRequestList) {
            if(voucherCreateRequest.getExpiryDate().compareTo(Calendar.getInstance())<0){
                messages.add(String.format("%s is expired", voucherCreateRequest.getVoucherName()));
                continue;
            }
            voucher = new Voucher(
                    voucherCreateRequest.getVoucherName(),
                    voucherCreateRequest.getDiscountAmt(),
                    voucherCreateRequest.getDate(),
                    voucherCreateRequest.getExpiryDate(),
                    voucherCreateRequest.getUserCount()
            );
            try {

                voucherRepository.save(voucher);
                voucherList.add(voucher);
                messages.add(String.format("%s added",voucherCreateRequest.getVoucherName()));

            } catch (DataIntegrityViolationException e) {
                Voucher existingVoucher = voucherRepository.findByVoucherName(voucherCreateRequest.getVoucherName());
                voucherRepository.delete(existingVoucher);
                voucherRepository.save(voucher);
                voucherList.add(voucher);
                messages.add(String.format("%s added",voucherCreateRequest.getVoucherName()));
            }
        }
        return new Response(true,"Vouchers Added",messages);


    }



    public String deleteVoucher(Long voucherId) {

        voucherRepository.deleteById(voucherId);
        return "Deleted";


    }

    public BigDecimal getVoucherByName(String voucherName) {
        try {
            return voucherRepository.findByVoucherName(voucherName).getDiscountAmt();
        }catch (NullPointerException e){
            return BigDecimal.valueOf(0);
        }
    }

    public boolean ifExist(String voucherName) {
        if(voucherName==null||voucherName.compareTo("")==0){
            return true;
        }else if(voucherRepository.findByVoucherName(voucherName)==null){
            return false;
        }

        return true;
    }

    public boolean ifExpired(String voucherName) {
        if(voucherName==null||voucherName.compareTo("")==0) {
            return false;
        }
        Voucher voucher =  voucherRepository.findByVoucherName(voucherName);
        if(voucher.getExpiryDate().compareTo(Calendar.getInstance())<0){
            return true;
        }
        return false;
    }

    public void updateUserCount(String voucherName) {
        if(voucherName==null||voucherName.compareTo("")==0) {
            return;
        }
        Voucher voucher = voucherRepository.findByVoucherName(voucherName);
        voucher.setUserCount(voucher.getUserCount()-1);
        voucherRepository.save(voucher);
    }

    public boolean ifUserCountReached(String voucherName) {
        if(voucherName==null||voucherName.compareTo("")==0) {
            return false;
        }
        Voucher voucher = voucherRepository.findByVoucherName(voucherName);
        if(voucher.getUserCount()==0){
            return true;
        }
        return false;
    }
}