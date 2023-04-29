package com.es.core.model.phone.dao;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {
    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phones = new ArrayList<>();
        PhoneRowMapper phoneRowMapper = new PhoneRowMapper();
        while (resultSet.next()) {
            Phone phone = new Phone();
            phone.setId(resultSet.getLong("id"));
            phone.setBrand(resultSet.getString("brand"));
            phone.setModel(resultSet.getString("model"));

            if (!phones.contains(phone)) {
                phone = phoneRowMapper.mapRow(resultSet, 0);
                phones.add(phone);
            }
            int index = phones.indexOf(phone);
            Set<Color> colors = phones.get(index).getColors();
            if (colors == Collections.EMPTY_SET) {
                colors = new HashSet<>();
            }

            Color color = new Color();
            color.setId(resultSet.getLong("id"));
            color.setCode(resultSet.getString("code"));
            colors.add(color);

            phones.get(index).setColors(colors);
        }
        return phones;
    }
}
