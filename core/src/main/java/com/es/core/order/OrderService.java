package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    long placeOrder(Order order) throws OutOfStockException;
    Order getOrder(Long id) throws OrderNotFound;

    Order getOrderBySecureId(String id) throws OrderNotFound;

    BigDecimal calculateSubtotal(List<OrderItem> orderItems);
}
