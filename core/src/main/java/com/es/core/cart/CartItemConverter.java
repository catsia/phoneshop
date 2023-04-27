package com.es.core.cart;

import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemConverter {
    @Resource
    private PhoneDao phoneDao;

    public List<CartItem> convertToCartItems(List<CartItemReduced> cartItemsReduced) {
        return cartItemsReduced.stream()
                .map(cartItemReduced -> new CartItem(phoneDao.get(cartItemReduced.getId()).get(), cartItemReduced.getQuantity()))
                .collect(Collectors.toList());
    }
}
