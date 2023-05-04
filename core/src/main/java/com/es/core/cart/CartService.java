package com.es.core.cart;

import java.util.List;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(List<CartItem> cartItem);

    void remove(Long phoneId);

    void removeAll();
}
