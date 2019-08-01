package com.example.e_travel.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.e_travel.model.City;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM cities WHERE id_country = :countryId")
    List<City> getCities(int countryId);

    @Query("SELECT * FROM cities")
    List<City> getCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCities(List<City> cities);

    @Query("DELETE FROM cities")
    void deleteTable();
}
