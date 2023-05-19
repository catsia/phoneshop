package com.es.phoneshop.web.controller.validator;

import com.es.core.cart.QuickCart;
import com.es.core.cart.QuickCartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class ValidatorForQuickCart implements Validator {
    @Resource
    private StockDao stockDao;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickCart.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickCart quickCart = (QuickCart) o;
        int index = 0;
        for (QuickCartItem cartItem : quickCart.getCartItems()) {
            if (cartItem.getPhoneName() == null && cartItem.getQuantity() == null) {
                return;
            }
            if (cartItem.getPhoneName().isBlank()) {
                errors.rejectValue("cartItems[" + index + "]", "Phone name is empty");
                return;
            }
            Optional<Phone> phone = phoneDao.get(cartItem.getPhoneName());
            if (!phone.isPresent()) {
                errors.rejectValue("cartItems[" + index + "]", "Phone does not exist");
                return;
            }
            if (!stockDao.get(phone.get().getId()).isPresent()) {
                errors.rejectValue("cartItems[" + index + "]", "Phone has no stock");
                return;
            }

            Stock currentStock = stockDao.get(phone.get().getId()).get();

            if (cartItem.getQuantity().isBlank()) {
                errors.rejectValue("cartItems[" + index + "]", "Quantity is empty");
                return;
            }
            if (!checkIfNumber(cartItem.getQuantity())) {
                errors.rejectValue("cartItems[" + index + "]", "Quantity is not a number");
            }
            Long quantity = Long.parseLong(cartItem.getQuantity());
            if (quantity < 0) {
                errors.rejectValue("cartItems[" + index + "]", "Quantity is negative");
            }
            if (quantity == 0) {
                errors.rejectValue("cartItems[" + index + "]", "Quantity is zero");
            }
            if (currentStock.getStock() - quantity < 0) {
                errors.rejectValue("cartItems[" + index + "]", "Quantity is more than in stock");
            }
            quickCart.getValidatedCartItems().put(phone.get(), quantity);
            index++;
        }
    }

    private boolean checkIfNumber(String quantity) {
        try {
            Long.parseLong(quantity);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}