package com.example.easyfind.database;

import com.example.easyfind.models.Business;

import java.util.List;

public interface BusinessService {

    List<Business> getAll();

    void insertAll(Business... businesses);

    void delete(Business business);
}
