package com.es.core.model.order.dao;

import com.es.core.model.order.Order;

import java.util.List;

public interface OrderDao {
    long save(Order order);
    List<Order> findAll();
}
