package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (phone.isPresent()) {
            CartItem cartItem = new CartItem(phone.get(), quantity);
            if (cart.getCartItems().contains(cartItem)) {
                update(cartItem);
            } else {
                cart.getCartItems().add(cartItem);
            }
            calculateTotalCost(cartItem);
            calculateTotalQuantity(cartItem);
        }
    }

    @Override
    public void update(CartItem cartItem) {
        int index = cart.getCartItems().indexOf(cartItem);
        Long currentQuantity = cart.getCartItems().get(index).getQuantity();
        cart.getCartItems().get(index).setQuantity(currentQuantity + cartItem.getQuantity());
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    public void calculateTotalCost(CartItem cartItem) {
        cart.setTotalCost(cart.getTotalCost().add
                (cartItem.getPhone().getPrice().
                        multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
    }

    public void calculateTotalQuantity(CartItem cartItem) {
        cart.setTotalQuantity(cart.getTotalQuantity() + cartItem.getQuantity());
    }
}
