package com.es.core.model.phone.dao;

import com.es.core.model.phone.Stock;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class JdbcStockDao implements StockDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String GET_STOCK_BY_PHONE_ID_QUERY = "select * from phones join stocks on stocks.phoneId = ?";

    @Override
    public Optional<Stock> get(Long key) {
        return jdbcTemplate.query(GET_STOCK_BY_PHONE_ID_QUERY, new BeanPropertyRowMapper<>(Stock.class), key).stream().findAny();
    }
}
