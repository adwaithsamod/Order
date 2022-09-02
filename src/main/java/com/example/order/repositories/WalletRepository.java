package com.example.order.repositories;

import com.example.order.entities.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> {
    @Query(nativeQuery = true, value = "select * from wallet where fk_user_id=:userId")
    Optional<Wallet> findByUserId(@Param("userId") Long userId);
}