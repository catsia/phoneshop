package com.es.core.model.phone.dao;

import com.es.core.model.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String query);
    int count(String query);
}
