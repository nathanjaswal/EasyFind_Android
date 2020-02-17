package com.example.easyfind.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.easyfind.models.Business;

@Database(entities = {Business.class}, version = 1, exportSchema = false)
@TypeConverters({CategoryListConverter.class, CoordinateConverter.class, LocationConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase = null;

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database_easyFind").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

    public abstract BusinessDao businessDao();
}

