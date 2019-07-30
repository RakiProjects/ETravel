package com.example.e_travel.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.CitiesActivity;
import com.example.e_travel.model.City;
import com.example.e_travel.response.CitiesResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;
import com.example.e_travel.room.ETravelRoomDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesRepository extends BaseRepository {

    private static final String TAG = CitiesRepository.class.getSimpleName();

    private static CitiesRepository citiesRepository;

    ETravelRoomDatabase database;

    public static CitiesRepository getInstance() {
        if (citiesRepository == null) {
            citiesRepository = new CitiesRepository();
        }
        return citiesRepository;
    }

    private CitiesRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCityList(final Context context, final MutableLiveData<CitiesResponse> citiesLiveData, final int countryId) {
        Call<ArrayList<City>> call = service.getCityList(countryId);

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    ArrayList<City> cityList = response.body();

                    database = ETravelRoomDatabase.getInstance(context);
                    database.cityDao().deleteTable();

                    for (City c : cityList) {
                        City city = new City(c);
                        database.cityDao().insertCities(city);
                        Log.v(TAG, "insert " + c.getName());
                    }
                    citiesLiveData.setValue(new CitiesResponse(cityList, null));

                } else {
                    Log.v(TAG, String.valueOf(response.code()));
                    //...
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                // no internet, room database
                database = ETravelRoomDatabase.getInstance(context);
                List<City> cityList = database.cityDao().getCities(countryId);
                Log.v(TAG, "size upita sa IDem je "+cityList.size());
                if (cityList.size() != 0) {
                    citiesLiveData.setValue(new CitiesResponse(cityList, t));
                } else {
                    citiesLiveData.setValue(new CitiesResponse(null, t));
                }
                ETravelRoomDatabase.destroyInstance();
            }
        });
    }
}
