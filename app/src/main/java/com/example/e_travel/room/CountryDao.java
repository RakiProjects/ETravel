package com.example.e_travel.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.e_travel.model.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM countries")
    List<Country> getCountries();

    @Insert
    void insertCountries(Country... countries);

    @Query("DELETE FROM countries")
    void deleteTable();
}
