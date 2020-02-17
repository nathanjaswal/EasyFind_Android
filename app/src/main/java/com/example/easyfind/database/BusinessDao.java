package com.example.easyfind.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.easyfind.models.Business;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BusinessDao {

    @Insert(onConflict = REPLACE)
    void insert(Business... businesses);

    @Delete
    void delete(Business business);

    @Query("SELECT * from business_table")
    List<Business> getBusiness();
}
