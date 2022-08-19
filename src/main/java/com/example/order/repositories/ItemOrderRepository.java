package com.example.order.repositories;

import com.example.order.entities.ItemOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrderRepository extends CrudRepository<ItemOrder,Long> {


    List<ItemOrder> findAllOrdersByOrderMasterOrderMasterId(Long orderMasterId);
}
    

