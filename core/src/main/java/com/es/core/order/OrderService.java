package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    long placeOrder(Order order) throws OutOfStockException;
    Order getOrder(Long id) throws OrderNotFound;

    Order getOrderBySecureId(String id) throws OrderNotFound;
    List<Order> getOrders();
    void updateStatus(Long id, OrderStatus orderStatus);
}
