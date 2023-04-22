package com.es.phoneshop.web.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class QuantityValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ValidatedJsonInfo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidatedJsonInfo quantity = (ValidatedJsonInfo) o;
        if (quantity.getQuantity() <= 0) {
            errors.rejectValue("quantity", "value.negative");
        }
    }
}