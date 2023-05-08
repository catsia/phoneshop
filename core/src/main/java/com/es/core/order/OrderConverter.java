package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderReduced;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderConverter {
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Resource
    private PhoneDao phoneDao;

    public Order convert(OrderReduced orderReduced) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : orderReduced.getOrderItems().entrySet()) {
            Long phoneId = entry.getKey();
            Long quantity = entry.getValue();
            Optional<Phone> phone = phoneDao.get(phoneId);
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(phone.get());
            orderItem.setQuantity(quantity);
            orderItems.add(orderItem);
        }

        order.setSecureId(orderReduced.getSecureId());
        order.setOrderItems(orderItems);
        order.setFirstName(orderReduced.getFirstName());
        order.setLastName(orderReduced.getLastName());
        order.setDeliveryAddress(orderReduced.getDeliveryAddress());
        order.setContactPhoneNo(orderReduced.getContactPhoneNo());
        order.setAdditionalInformation(orderReduced.getAdditionalInformation());
        order.setSubtotal(calculateSubtotal(orderItems));
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    private BigDecimal calculateSubtotal(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPhone().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
