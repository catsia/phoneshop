package com.es.phoneshop.web.controller.validator;

import com.es.core.cart.CartItemReduced;
import com.es.core.cart.CartItemReducedDto;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;


public class QuantityValidatorForDto implements Validator {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemReducedDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemReducedDto cartItemReducedDto = (CartItemReducedDto) o;
        int index = 0;
        for(CartItemReduced cartItemReduced: cartItemReducedDto.getCartItemReduced()) {
            Stock currentStock;
            if (!stockDao.get(cartItemReduced.getId()).isPresent()) {
                errors.rejectValue("cartItemReduced["+index+"]", "Phone has no stock");
            }
            currentStock = stockDao.get(cartItemReduced.getId()).get();

            if (cartItemReduced.getQuantity() < 0) {
                errors.rejectValue("cartItemReduced["+index+"]", "Quantity is negative");
            }
            if (cartItemReduced.getQuantity() == 0) {
                errors.rejectValue("cartItemReduced["+index+"]", "Quantity is zero");
            }
            if (currentStock.getStock() - cartItemReduced.getQuantity() < 0) {
                errors.rejectValue("cartItemReduced["+index+"]", "Quantity is more than in stock");
            }
            index++;
        }
    }
}