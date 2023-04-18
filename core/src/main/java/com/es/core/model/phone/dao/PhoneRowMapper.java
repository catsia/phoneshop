package com.es.core.model.phone.dao;

import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneRowMapper implements RowMapper<Phone> {
    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setBrand(resultSet.getString("brand"));
        phone.setModel(resultSet.getString("model"));
        phone.setPrice(BigDecimal.valueOf(resultSet.getFloat("price")));
        phone.setDisplaySizeInches(BigDecimal.valueOf(resultSet.getFloat("displaySizeInches")));
        phone.setWeightGr(resultSet.getInt("weightGr"));
        phone.setLengthMm(BigDecimal.valueOf(resultSet.getFloat("lengthMm")));
        phone.setWidthMm(BigDecimal.valueOf(resultSet.getFloat("widthMm")));
        phone.setHeightMm(BigDecimal.valueOf(resultSet.getFloat("heightMm")));
        phone.setAnnounced(resultSet.getDate("announced"));
        phone.setDeviceType(resultSet.getString("deviceType"));
        phone.setOs(resultSet.getString("os"));
        phone.setDisplayResolution(resultSet.getString("displayResolution"));
        phone.setPixelDensity(resultSet.getInt("pixelDensity"));
        phone.setDisplayTechnology(resultSet.getString("displayTechnology"));
        phone.setBackCameraMegapixels(BigDecimal.valueOf(resultSet.getFloat("backCameraMegapixels")));
        phone.setFrontCameraMegapixels(BigDecimal.valueOf(resultSet.getFloat("frontCameraMegapixels")));
        phone.setRamGb(BigDecimal.valueOf(resultSet.getFloat("ramGb")));
        phone.setInternalStorageGb(BigDecimal.valueOf(resultSet.getFloat("internalStorageGb")));
        phone.setBatteryCapacityMah(resultSet.getInt("batteryCapacityMah"));
        phone.setTalkTimeHours(BigDecimal.valueOf(resultSet.getFloat("talkTimeHours")));
        phone.setStandByTimeHours(BigDecimal.valueOf(resultSet.getFloat("standByTimeHours")));
        phone.setBluetooth(resultSet.getString("bluetooth"));
        phone.setPositioning(resultSet.getString("positioning"));
        phone.setImageUrl(resultSet.getString("imageUrl"));
        phone.setDescription(resultSet.getString("description"));
        return phone;
    }
}
