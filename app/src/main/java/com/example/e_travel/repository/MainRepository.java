package com.example.e_travel.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.Country;
import com.example.e_travel.response.MainResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;
import com.example.e_travel.room.ETravelRoomDatabase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainRepository extends BaseRepository {

    private static final String TAG = MainRepository.class.getSimpleName();

    private static MainRepository mainRepository;

    ETravelRoomDatabase database;

    public static MainRepository getInstance() {
        if (mainRepository == null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }

    private MainRepository() {
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCountryList(final Context context, final MutableLiveData countriesLiveData) {

        Call<ArrayList<Country>> call = service.getCountryList();

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Country> countryList = response.body();

                    database = ETravelRoomDatabase.getInstance(context);
                    database.countryDao().deleteTable();
                    for (Country c : countryList) {
                        Country country = new Country(c);
                        database.countryDao().insertCountries(country);
                    }

                    countriesLiveData.setValue(new MainResponse(countryList, response.code(), null));
                    //}
                } else {
                    try {
                        String error =  response.errorBody().string();
                    } catch (IOException e) {
                        countriesLiveData.setValue(new MainResponse(null, -1, e));
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                // no internet, room database
                database = ETravelRoomDatabase.getInstance(context);
                List<Country> countryList = database.countryDao().getCountries();
                if (countryList.size() != 0) {
                    countriesLiveData.setValue(new MainResponse(countryList, 0, t));
                }else{
                    countriesLiveData.setValue(new MainResponse(null, 0, t));
                }
                ETravelRoomDatabase.destroyInstance();
            }
        });
    }
}
