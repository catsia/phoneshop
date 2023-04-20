package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
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
            cart.getCartItems().add(new CartItem(phone.get(), quantity));
            cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
            cart.setTotalCost(cart.getTotalCost().add
                    (phone.get().getPrice().
                            multiply(BigDecimal.valueOf(quantity))));
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
