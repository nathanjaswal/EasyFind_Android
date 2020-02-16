package com.example.easyfind.database;

import androidx.room.TypeConverter;

import com.example.easyfind.models.BusinessLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LocationConverter {

    @TypeConverter
    public static BusinessLocation fromString(String value) {
        Type type = new TypeToken<BusinessLocation>(){}.getType();
        return new Gson().fromJson(value, type);
    }
    @TypeConverter
    public static String fromList(BusinessLocation location) {
        Gson gson = new Gson();
        String json = gson.toJson(location);
        return json;
    }
}
