package com.example.order.services;

import com.example.order.responseModel.Response;
import com.example.order.entities.ItemOrder;
import com.example.order.entities.OrderMaster;
import com.example.order.repositories.ItemOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ItemOrderService {

    @Autowired
    private ItemOrderRepository itemOrderRepository;



    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date date;

    public Response getAllOrders() {
        List<ItemOrder> orders = new ArrayList<>();
        itemOrderRepository.findAll().forEach(orders::add);
        return new Response(true, "Fetch all orders", orders);
    }

    public ItemOrder getItemOrderById(Long id){
            return itemOrderRepository.findById(id).get();
    }




    public void pushItemOrder(OrderMaster orderMaster, Long productId,Long quantity, BigDecimal price, String deliveryStatus, BigDecimal discount) {
        Date currentSqlDate = new Date(System.currentTimeMillis());
        ItemOrder order = new ItemOrder(orderMaster,productId,quantity,price,deliveryStatus,discount);
        itemOrderRepository.save(order);
    }



    public List<ItemOrder> getAllOrdersByOrderMasterId(Long orderMasterId) {
        return itemOrderRepository.findAllOrdersByOrderMasterOrderMasterId(orderMasterId);
    }

    public void pushAll(List<ItemOrder> itemOrderList) {
        itemOrderRepository.saveAll(itemOrderList);
    }

    public Response updateStatus(Long orderId, String status) {
            ItemOrder itemOrder = itemOrderRepository.findById(orderId).get();
            itemOrder.setDeliveryStatus(status);
            itemOrderRepository.save(itemOrder);
            return new Response(true,"Status updated successfully",itemOrder);
    }

    public Response bulkStatusUpdate(List<Long> idList) {

           Iterable<ItemOrder> itemOrderList = itemOrderRepository.findAllById(idList);
           for(ItemOrder itemOrder : itemOrderList){
               itemOrder.setDeliveryStatus("Delivered");
            itemOrderRepository.saveAll(itemOrderList);
            }
           if(itemOrderList.equals(new ArrayList<>())){
               return new Response(false,"No such id",null);
            }
            return new Response(true,"Status Updated",itemOrderList);
    }


    public Boolean checkIfAllDelivered(Long orderId) {
        ItemOrder itemOrder = itemOrderRepository.findById(orderId).get();
       Long orderMasterId = itemOrder.getOrderMaster().getOrderMasterId();
       List<ItemOrder> itemOrderList = itemOrderRepository.findAllOrdersByOrderMasterOrderMasterId(orderMasterId);
        for(ItemOrder item : itemOrderList){
            if(item.getDeliveryStatus().compareTo("Delivered")!=0){
                return false;
            }
        }
        return true;
    }

    public Boolean checkIfAllCancelled(Long orderMasterId) {

        List<ItemOrder> itemOrderList = itemOrderRepository.findAllOrdersByOrderMasterOrderMasterId(orderMasterId);
        for(ItemOrder item : itemOrderList){
            if(item.getDeliveryStatus().compareTo("Cancelled")!=0){
                return false;
            }
        }
        return true;
    }

    public void cancelOrder(ItemOrder itemOrder) {
        itemOrder.setDeliveryStatus("Cancelled");
        itemOrderRepository.save(itemOrder);
    }
}

