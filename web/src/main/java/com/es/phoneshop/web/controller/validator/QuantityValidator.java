package com.es.phoneshop.web.controller.validator;

import com.es.core.cart.CartItemReduced;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;


public class QuantityValidator implements Validator {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemReduced.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemReduced cartItemReduced = (CartItemReduced) o;
        Stock currentStock;
        if (!stockDao.get(cartItemReduced.getId()).isPresent()) {
            errors.rejectValue("id", "Phone has no stock");
        }
        currentStock = stockDao.get(cartItemReduced.getId()).get();

        if (cartItemReduced.getQuantity() < 0) {
            errors.rejectValue("quantity", "Quantity is negative");
        }
        if (cartItemReduced.getQuantity() == 0) {
            errors.rejectValue("quantity", "Quantity is zero");
        }
        if (currentStock.getStock() - cartItemReduced.getQuantity() < 0) {
            errors.rejectValue("quantity", "Quantity is more than in stock");
        }
    }
}