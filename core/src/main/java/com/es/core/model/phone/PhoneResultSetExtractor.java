package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {
    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Phone> phones = new HashMap<>();
        PhoneRowMapper phoneRowMapper = new PhoneRowMapper();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Phone phone = phones.get(id);

            if (phone == null) {
                phone = phoneRowMapper.mapRow(resultSet, 0);
                phones.put(phone.getId(), phone);
            }

            Set<Color> colors = phones.get(id).getColors();
            if (colors == Collections.EMPTY_SET) {
                colors = new HashSet<>();
            }

            Color color = new Color();
            color.setId(resultSet.getLong("id"));
            color.setCode(resultSet.getString("code"));
            colors.add(color);

            phones.get(id).setColors(colors);
        }
        return new ArrayList<>(phones.values());
    }
}
