package com.es.core.model.phone.dao;

import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final String GET_BY_ID_QUERY = "select * from phones left join phone2color on phones.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id where phones.id= ?";
    private final String INSERT_QUERY = "insert into phones (id, brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_ALL_QUERY = "select * from (select * from phones join stocks on stocks.phoneId = phones.id and stocks.stock > 0 limit ? offset ?) as phone left join phone2color on phone.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id";

    private final String FIND_ALL_QUERY_WITH_SORT_PARAMETERS = "select * from (select * from phones join stocks on stocks.phoneId = phones.id and stocks.stock > 0 order by %s limit ? offset ?) as phone left join phone2color on phone.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id";
    private final String FIND_ALL_QUERY_WITH_SORT_PARAMETERS_ADN_SEARCH = "select * from (select * from phones join stocks on stocks.phoneId = phones.id and stocks.stock > 0 where lower(phones.model) like lower('%%%s%%') or lower(phones.model)=lower('%s') order by %s limit ? offset ?) as phone left join phone2color on phone.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id";
    private final String FIND_ALL_QUERY_WITH_SEARCH = "select * from (select * from phones join stocks on stocks.phoneId = phones.id and stocks.stock > 0 where lower(phones.model) like lower('%%%s%%') or lower(phones.model)=lower('%s') limit ? offset ?) as phone left join phone2color on phone.id = phone2color.phoneId left join colors on phone2color.colorId = colors.id";

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(GET_BY_ID_QUERY, new PhoneResultSetExtractor(), key);
        return Optional.ofNullable(!phones.isEmpty() ? phones.get(0) : null);
    }

    public void save(final Phone phone) {
        jdbcTemplate.update(INSERT_QUERY, phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(), phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(), phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(), phone.getDescription());
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL_QUERY, new PhoneResultSetExtractor(), limit, offset);
    }

    @Override
    public List<Phone> findAllSortParametersAndSearch(int offset, int limit, SortField sortField, SortOrder sortOrder, String query) {
        String find = String.format(FIND_ALL_QUERY_WITH_SORT_PARAMETERS_ADN_SEARCH, query, query, sortField.toString() + " " + sortOrder.toString());
        return jdbcTemplate.query(find, new PhoneResultSetExtractor(), limit, offset);
    }

    @Override
    public List<Phone> findAllSearch(int offset, int limit, String query) {
        String find = String.format(FIND_ALL_QUERY_WITH_SEARCH, query, query);
        return jdbcTemplate.query(find, new PhoneResultSetExtractor(), limit, offset);
    }
}
