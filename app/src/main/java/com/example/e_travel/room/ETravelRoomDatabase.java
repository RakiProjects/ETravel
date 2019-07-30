package com.example.e_travel.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.e_travel.model.City;
import com.example.e_travel.model.Country;

@Database(entities = {Country.class, City.class},  exportSchema = false, version = 2)
public abstract class ETravelRoomDatabase extends RoomDatabase {

    private static final String DB_NAME = "eTravel_db";
    private static ETravelRoomDatabase instance;

    public static synchronized ETravelRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ETravelRoomDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public abstract CountryDao countryDao();

    public abstract CityDao cityDao();
}
