package com.seanlindev.springframework.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemDto implements Serializable {
    private String productId;
    private String name;
    private BigDecimal price;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
