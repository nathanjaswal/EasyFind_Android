package com.example.easyfind.database;

import android.content.Context;

import com.example.easyfind.models.Business;

import java.util.List;

public class BusinessServiceImpl implements BusinessService {

    private BusinessDao businessDao;

    public BusinessServiceImpl(Context context) {
        businessDao = AppDatabase.getInstance(context).businessDao();
    }

    @Override
    public List<Business> getAll() {
        return businessDao.getBusiness();
    }

    @Override
    public void insertAll(Business... businesses) {
        businessDao.insert(businesses);
    }

    @Override
    public void delete(Business business) {
        businessDao.delete(business);
    }
}