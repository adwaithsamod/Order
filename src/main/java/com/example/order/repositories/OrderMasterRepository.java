package com.example.order.repositories;

import com.example.order.entities.OrderMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterRepository extends CrudRepository<OrderMaster,Long> {


    List<OrderMaster> findAllByUsersId(Long userId);
}