package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.List;

@Component
public class JdbcOrderDao implements OrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String FIND_ALL_QUERY = "select * from orders";

    @Override
    public long save(Order order) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("firstName", order.getFirstName())
                .addValue("lastName", order.getLastName())
                .addValue("deliveryAddress", order.getDeliveryAddress())
                .addValue("contactPhoneNo", order.getContactPhoneNo())
                .addValue("additionInformation", order.getAdditionalInformation())
                .addValue("total", order.getTotalPrice())
                .addValue("status", order.getStatus().name(), Types.VARCHAR);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("orders");
        jdbcInsert.usingGeneratedKeyColumns("id");

        return (long) jdbcInsert.executeAndReturnKey(parameters);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, new BeanPropertyRowMapper<>(Order.class));
    }
}
