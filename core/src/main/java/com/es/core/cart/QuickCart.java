package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.util.HashMap;
import java.util.List;

public class QuickCart {
    private List<QuickCartItem> cartItems;
    private HashMap<Phone, Long> validatedCartItems;

    public List<QuickCartItem> getCartItems() {
        return cartItems;
    }

    public QuickCart() {
        validatedCartItems = new HashMap<>();
    }

    public void setCartItems(List<QuickCartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public HashMap<Phone, Long> getValidatedCartItems() {
        return validatedCartItems;
    }

    public void setValidatedCartItems(HashMap<Phone, Long> validatedCartItems) {
        this.validatedCartItems = validatedCartItems;
    }
}
