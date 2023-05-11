package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    long save(Order order);
    List<Order> findAll();
    Optional<Order> get(Long key);

    Optional<Order> getBySecureId(String key);
    void updateStatus(Long key, OrderStatus orderStatus);
}
