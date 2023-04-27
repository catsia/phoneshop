package com.es.core.cart;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class CartItemReducedDto implements Serializable {
    @Valid
    private List<CartItemReduced> cartItemReduced;

    public CartItemReducedDto() {
    }

    public CartItemReducedDto(List<CartItemReduced> cartItemReduced) {
        this.cartItemReduced = cartItemReduced;
    }

    public List<CartItemReduced> getCartItemReduced() {
        return cartItemReduced;
    }

    public void setCartItemReduced(List<CartItemReduced> cartItemReduced) {
        this.cartItemReduced = cartItemReduced;
    }
}
