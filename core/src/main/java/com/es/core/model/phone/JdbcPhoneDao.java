package com.es.core.model.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String findAllQuery = "select * from phones join phone2color on phones.id = phone2color.phoneId join colors on phone2color.colorId = colors.id ";

    public Optional<Phone> get(final Long key) {
        throw new UnsupportedOperationException("TODO");
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(findAllQuery + "offset" + offset + " limit " + limit, new PhoneResultSetExtractor());
    }
}
