package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void save(Order order);

    List<OrderItem> getOrderItems(Long key);
}
