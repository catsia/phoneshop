package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
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
                int index = cart.getCartItems().indexOf(cartItem);
                Long currentQuantity = cart.getCartItems().get(index).getQuantity();
                cart.getCartItems().get(index).setQuantity(cartItem.getQuantity());
            } else {
                cart.getCartItems().add(cartItem);
            }
            calculateTotalCost();
            calculateTotalQuantity();
        }
    }

    @Override
    public void update(List<CartItem> cartItems) {
        cartItems.forEach(cartItem -> {
            int index = cart.getCartItems().indexOf(cartItem);
            cart.getCartItems().get(index).setQuantity(cartItem.getQuantity());
        });
        calculateTotalCost();
        calculateTotalQuantity();
    }

    @Override
    public void remove(Long phoneId) {
        Optional<Phone> phone = phoneDao.get(phoneId);
        phone.ifPresent(value -> cart.getCartItems().remove(new CartItem(value, null)));
        calculateTotalCost();
        calculateTotalQuantity();
    }

    public void calculateTotalCost() {
        cart.setTotalCost(cart.getCartItems().stream()
                .map(item -> item.getPhone().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void calculateTotalQuantity() {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum());
    }
}
