package com.es.core.model.order.dao;

import com.es.core.model.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    long save(Order order);
    List<Order> findAll();
    Optional<Order> get(Long key);
}
