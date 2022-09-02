package com.example.order.repositories;

import com.example.order.entities.Voucher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher,Long> {
    public Voucher findByVoucherName(String voucherName);
}