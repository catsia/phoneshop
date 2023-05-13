package com.es.phoneshop.web.controller.validator;

import com.es.core.model.order.OrderReduced;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Optional;


public class OrderValidator implements Validator {

    @Resource
    private StockDao stockDao;

    private static final String PHONE_REGEX = "^\\+?\\d{1,3}\\s?(\\d{3}\\s?){2}\\d{2}\\s?\\d{2}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderReduced.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderReduced order = (OrderReduced) o;
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            errors.rejectValue("orderItems", "Nothing in order");
        } else {
            order.getOrderItems().forEach((id, quantity) -> {
                Optional<Stock> currentStock = stockDao.get(id);
                if (!currentStock.isPresent() || currentStock.get().getStock() - quantity < 0) {
                    errors.rejectValue("orderItems", "More than in stock");
                }
            });
            order.getOrderItems().entrySet().
                    removeIf(orderItem -> !stockDao.get(orderItem.getKey()).isPresent()
                            || stockDao.get(orderItem.getKey()).get().getStock() < orderItem.getValue());
        }

        if (order.getFirstName().isBlank()) {
            errors.rejectValue("firstName", "First name must not be empty");
        }

        if (order.getLastName().isBlank()) {
            errors.rejectValue("lastName", "Last name must not be empty");
        }

        if (order.getDeliveryAddress().isBlank()) {
            errors.rejectValue("deliveryAddress", "Delivery address must not be empty");
        }

        if (order.getContactPhoneNo().isBlank()) {
            errors.rejectValue("contactPhoneNo", "Contact phone number must not be empty");
        } else {
            if (!order.getContactPhoneNo().matches(PHONE_REGEX)) {
                errors.rejectValue("contactPhoneNo", "Invalid phone number format");
            }
        }

        if (order.getAdditionalInformation().length() > 1000) {
            errors.rejectValue("additionalInformation", "Additional information must not exceed 1000 characters");
        }

    }
}