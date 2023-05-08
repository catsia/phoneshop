package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;

public interface OrderService {
    Order createOrder(Cart cart);
    long placeOrder(Order order) throws OutOfStockException;
    Order getOrder(Long id) throws OrderNotFound;

    Order getOrderBySecureId(String id) throws OrderNotFound;
}
