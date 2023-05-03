package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JdbcOrderItemDao implements OrderItemDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StockDao stockDao;

    private final String INSERT_QUERY = "insert into orderItems (orderId, phoneId, quantity) values (?, ?, ?)";

    @Override
    public void save(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            Long phoneId = orderItem.getPhone().getId();
            Long quantity = orderItem.getQuantity();
            Stock stock = stockDao.get(phoneId).get();
            stockDao.update(phoneId, stock.getStock() - quantity);
            jdbcTemplate.update(INSERT_QUERY, order.getId(), phoneId, quantity);
        });
    }
}
