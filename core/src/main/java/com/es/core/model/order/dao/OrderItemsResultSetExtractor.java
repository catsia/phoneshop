package com.es.core.model.order.dao;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderItemsResultSetExtractor implements ResultSetExtractor<List<OrderItem>> {
    @Override
    public List<OrderItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, OrderItem> orderItemMap = new HashMap<>();
        PhoneRowMapper phoneRowMapper = new PhoneRowMapper();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            OrderItem orderItem = orderItemMap.get(id);
            Phone phone = new Phone();

            if (orderItem == null) {
                orderItem = new OrderItem();
                orderItem.setQuantity(resultSet.getLong("quantity"));
                phone = phoneRowMapper.mapRow(resultSet, 0);
                orderItem.setPhone(phone);
                orderItemMap.put(id, orderItem);
            }
            Set<Color> colors = phone.getColors();

            if (colors == Collections.EMPTY_SET) {
                colors = new HashSet<>();
            }
            Color color = new Color();
            color.setId(resultSet.getLong("id"));
            color.setCode(resultSet.getString("code"));
            colors.add(color);
            orderItemMap.get(id).getPhone().setColors(colors);
        }
        return new ArrayList<>(orderItemMap.values());

    }
}
