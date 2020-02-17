package com.example.easyfind.database;

import androidx.room.TypeConverter;

import com.example.easyfind.models.Coordinates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CoordinateConverter {

    @TypeConverter
    public static Coordinates fromString(String value) {
        Type type = new TypeToken<Coordinates>(){}.getType();
        return new Gson().fromJson(value, type);
    }
    @TypeConverter
    public static String fromList(Coordinates coordinates) {
        Gson gson = new Gson();
        String json = gson.toJson(coordinates);
        return json;
    }
}
