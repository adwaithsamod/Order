package com.example.order.controllers.request;

public class ProductCreateRequest {

    private Long productId;

    private Long quantity;




    public ProductCreateRequest() {
    }

    public ProductCreateRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}