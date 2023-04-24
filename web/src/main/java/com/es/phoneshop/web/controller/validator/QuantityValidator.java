package com.es.phoneshop.web.controller.validator;

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
        return ValidatedJsonInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidatedJsonInfo validatedJsonInfo = (ValidatedJsonInfo) o;
        Stock currentStock;
        if (!stockDao.get(validatedJsonInfo.getPhoneId()).isPresent()) {
            errors.rejectValue("phoneId", "Phone has no stock");
        }
        currentStock = stockDao.get(validatedJsonInfo.getPhoneId()).get();

        if (validatedJsonInfo.getQuantity() < 0) {
            errors.rejectValue("quantity", "Quantity is negative");
        }
        if (validatedJsonInfo.getQuantity() == 0) {
            errors.rejectValue("quantity", "Quantity is zero");
        }
        if (currentStock.getStock() - validatedJsonInfo.getQuantity() <= 0) {
            errors.rejectValue("quantity", "Quantity is more than in stock");
        }
    }
}