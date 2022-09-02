package com.example.order.services;


import com.example.order.responseModel.Response;
import com.example.order.controllers.request.ProductCreateRequest;
import com.example.order.controllers.request.WalletTopUpRequest;
import com.example.order.entities.*;
import com.example.order.repositories.OrderMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderMasterService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private  ItemOrderService itemOrderService;

    public void push(OrderMaster orderMaster){
        orderMasterRepository.save(orderMaster);
    }

    public List<ItemOrder> getAllOrdersByUser(Long userId){
        List<OrderMaster> orderMasterList = orderMasterRepository.findAllByUsersId(userId);
        List<ItemOrder> itemOrderList = new ArrayList<>();
        for(OrderMaster orderMasterItem : orderMasterList){
            itemOrderList.addAll(itemOrderService.getAllOrdersByOrderMasterId(orderMasterItem.getOrderMasterId()));
        }
        return itemOrderList;
//        BigDecimal totalDiscount = o
    }

    @Transactional
    public Response buy(ArrayList<ProductCreateRequest> productCreateRequest, String voucherName, Long addressId, Long userId) {

        if(addressId==null) {
            return new Response(false, "Provide a valid address", null);
        }

        DeliveryAddress deliveryAddress = deliveryAddressService.getAddressById(addressId);

        if(!(voucherService.ifExist(voucherName))) {
            return new Response(false, "Incorrect Voucher Code", null);
        } else if (voucherService.ifExpired(voucherName)) {
            return new Response(false,"Voucher has Expired",null);
        } else if (voucherService.ifUserCountReached(voucherName)) {
            return new Response(false,"Voucher User Limit has reached",null);
        } else{
            voucherService.updateUserCount(voucherName);
        }


        BigDecimal orderMasterTotalDiscount = voucherService.getVoucherByName(voucherName);

        List<String> messages = new ArrayList<>();
        String status="Processing";

        BigDecimal orderMasterPrice = BigDecimal.valueOf(0);
        Date currentSqlDate = new Date(System.currentTimeMillis());
        OrderMaster orderMaster = new OrderMaster(currentSqlDate,orderMasterPrice,orderMasterTotalDiscount,status, usersService.getUser(userId));
//        orderMasterService.push(orderMaster);
        Wallet wallet = walletService.getWalletByUserId(userId);
        BigDecimal walletBalance = wallet.getWalletBalance();

        List<ItemOrder> itemOrderList = new ArrayList<>();

        for(ProductCreateRequest item : productCreateRequest){

            Long productId = item.getProductId();
            Product product = productService.getProduct(productId);


            Long stock = product.getStock();
            Long quantity = item.getQuantity();

            if (stock == 0) {
                messages.add(String.format("%s Out of stock",product.getProductName()));
                continue;
            }
            if(stock < quantity){
                messages.add(String.format("For selected item %s: Only %d pieces available",product.getProductName(),product.getStock()));
                continue;
            }

            BigDecimal price = product.getPrice();
            BigDecimal discount = product.getDiscount();

            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalDiscount =discount.multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalPriceAfterDiscount = totalPrice.subtract(totalDiscount);

            if (totalPriceAfterDiscount.compareTo(walletBalance) > 0) {
                messages.add(String.format( "Not enough balance to buy product: %s",product.getProductName()));
                continue;
            }

            productService.decrementStock(productId,quantity);

            status="Not Dispatched";
            ItemOrder itemOrder =new ItemOrder(orderMaster, productId,quantity,price,status,discount);
            itemOrderList.add(itemOrder);
            messages.add(String.format("Ordered %d no of item: %s",quantity,product.getProductName()));
//            pushItemOrder(orderMaster, productId, quantity, price.subtract(discount), status, discount);
            orderMasterTotalDiscount=orderMasterTotalDiscount.add(totalDiscount);
            orderMasterPrice=orderMasterPrice.add(totalPrice);

        }
        status="Completed";


        itemOrderService.pushAll(itemOrderList);

        BigDecimal availableDiscount =  discountService.getAvailableDiscount(orderMasterPrice);

        orderMasterTotalDiscount=orderMasterTotalDiscount.add(availableDiscount);

        BigDecimal orderMasterTotalPriceAfterDiscount = orderMasterPrice.subtract(orderMasterTotalDiscount);
        orderMaster.setTotalDiscount(orderMasterTotalDiscount);
        if(orderMasterTotalPriceAfterDiscount.compareTo(BigDecimal.valueOf(0))<=0){
            orderMasterTotalPriceAfterDiscount=BigDecimal.valueOf(0);
        }

        orderMaster.setTotalPrice(orderMasterTotalPriceAfterDiscount);
        orderMaster.setStatus(status);
        orderMaster.setDeliveryAddress(deliveryAddress);
        Long walletId = wallet.getWalletId();
        walletService.decrementWallet(walletId, orderMasterTotalPriceAfterDiscount, walletBalance );
//        String temp = "hi";
        push(orderMaster);


        notificationService.send(userId);

        return new Response(true,"Order Completed",messages);

    }

    @Transactional
    public Response updateStatus(Long orderId, String status) {
        Response response = itemOrderService.updateStatus(orderId,status);
        if(response.getIsValid()) {
//            Long orderMasterId = itemOrderService.getMasterIdByOrderId(orderId).getOrderMasterId();
            Boolean isDelivered = itemOrderService.checkIfAllDelivered(orderId);
            if(isDelivered){
                OrderMaster orderMaster = itemOrderService.getItemOrderById(orderId).getOrderMaster();
                orderMaster.setStatus("Delivered");
                orderMasterRepository.save(orderMaster);
            }
        }
        return response;
    }

    @Transactional
    public Response bulkStatusUpdate(List<Long> idList) {
        Response response = itemOrderService.bulkStatusUpdate(idList);
        if(response.getIsValid()) {
            for (Long id : idList){
                try {
                    Boolean isDelivered = itemOrderService.checkIfAllDelivered(id);
                    if (isDelivered) {
                        OrderMaster orderMaster = itemOrderService.getItemOrderById(id).getOrderMaster();
                        orderMaster.setStatus("Delivered");
                        orderMasterRepository.save(orderMaster);
                    }
                }catch (NoSuchElementException e){
                    continue;
                }
            }
        }
        return response;
    }

    @Transactional
    public Response cancelOrder(Long orderId) {
        ItemOrder itemOrder = itemOrderService.getItemOrderById(orderId);
        if(itemOrder.getDeliveryStatus().compareTo("Delivered")!=0 && itemOrder.getDeliveryStatus().compareTo("Cancelled")!=0) {
            OrderMaster orderMaster = itemOrder.getOrderMaster();
            Long orderMasterId = itemOrder.getOrderMaster().getOrderMasterId();
            List<ItemOrder> itemOrderList = itemOrderService.getAllOrdersByOrderMasterId(orderMasterId);
            BigDecimal itemOrderDiscount = BigDecimal.valueOf(0);
            for (ItemOrder item : itemOrderList) {
                itemOrderDiscount = itemOrderDiscount.add(item.getDiscount().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
            BigDecimal masterOrderDiscount = orderMaster.getTotalDiscount();
            if (masterOrderDiscount.compareTo(itemOrderDiscount) > 0) {
                return new Response(false, "Cannot cancel orders which used vouchers or marginal discount", null);
            }

            itemOrderService.cancelOrder(itemOrder);
            Boolean isCancelled = itemOrderService.checkIfAllCancelled(orderMasterId);
            if (isCancelled) {

                orderMaster.setStatus("Cancelled");
                orderMasterRepository.save(orderMaster);
            }
            Users users = orderMaster.getUsers();
            Wallet wallet = walletService.getWalletByUserId(users.getId());
//                wallet.setWalletBalance();
            WalletTopUpRequest walletTopUpRequest = new WalletTopUpRequest(users.getId(), itemOrder.getPrice().subtract(itemOrder.getDiscount()).multiply(BigDecimal.valueOf(itemOrder.getQuantity())));
            walletService.topUp(walletTopUpRequest);
            Product product = productService.getProduct(itemOrder.getProductId());
            product.setStock(product.getStock() + itemOrder.getQuantity());
            return new Response(true, "Order Cancelled", itemOrder);
        } else if (itemOrder.getDeliveryStatus().compareTo("Delivered")==0) {
            return new Response(false,"Already Delivered Products cannot be cancelled",null);
        } else if (itemOrder.getDeliveryStatus().compareTo("Cancelled")==0) {
            return new Response(false,"Already cancelled Products cannot be cancelled",null);
        }else
        {
            return new Response(false,"Invalid Status",null);
        }
    }


}