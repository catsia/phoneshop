package com.es.core.cart;

import javax.validation.constraints.Min;

public class CartItemReduced {
    private Long id;
    @Min(1)
    private Long quantity;

    public CartItemReduced() {
    }

    public CartItemReduced(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
