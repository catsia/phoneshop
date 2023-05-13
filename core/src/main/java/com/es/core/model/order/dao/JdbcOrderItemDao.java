package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class JdbcOrderItemDao implements OrderItemDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private StockDao stockDao;

    @Resource
    private PhoneDao phoneDao;

    private final String INSERT_QUERY = "insert into orderItems (orderId, phoneId, quantity) values (?, ?, ?)";
    private final String GET_BY_ID_QUERY = "select * from orderItems left join phones on phones.id=orderItems.phoneId  left join phone2color on orderItems.phoneId = phone2color.phoneId left join colors on phone2color.colorId = colors.id where orderItems.orderId=?";

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

    @Override
    public List<OrderItem> getOrderItems(Long key) {
        return jdbcTemplate.query(GET_BY_ID_QUERY, new OrderItemsResultSetExtractor(), key);
    }
}
