package com.es.core.cart;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void addPhone(Phone phone, Long quantity);

    void update(List<CartItem> cartItem);

    void remove(Long phoneId);

    void removeAll();

    void addQuickCart(QuickCart quickCart);
}
