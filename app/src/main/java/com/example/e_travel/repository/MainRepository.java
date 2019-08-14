package com.example.e_travel.repository;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.Country;
import com.example.e_travel.response.MainResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;
import com.example.e_travel.room.CountryDao;
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
    CountryDao countryDao;

    public static MainRepository getInstance(Context context) {
        if (mainRepository == null) {
            mainRepository = new MainRepository(context);
        }
        return mainRepository;
    }

    private MainRepository(Context context) {
        service = RetrofitInstance.createService(WebApi.class);
        database = ETravelRoomDatabase.getInstance(context);
        countryDao = database.countryDao();
    }

    public void getCountryList(final MutableLiveData countriesLiveData) {

        Call<ArrayList<Country>> call = service.getCountryList();

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Country> countryList = response.body();

                    insertCountries(countryList, countryDao);

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
               selectCountries(countryDao, countriesLiveData);
            }
        });
    }

    private  void insertCountries(List<Country> countries, CountryDao countryDao){
        new insertCountriesAsyncTask(countryDao).execute(countries);
    }

    private static class insertCountriesAsyncTask extends AsyncTask<List<Country>, Void, Void>{

        CountryDao countryDao;

        insertCountriesAsyncTask(CountryDao dao){
            countryDao = dao;
        }

        @Override
        protected Void doInBackground(List<Country>... lists) {
            countryDao.deleteTable();
            countryDao.insertCountries(lists[0]);
            return null;
        }
    }

     private void selectCountries(CountryDao countryDao, MutableLiveData countriesLiveData){
       new selectCountriesAsyncTask(countryDao, countriesLiveData).execute();
    }

    private static class selectCountriesAsyncTask extends AsyncTask<Void, Void, List<Country>>{

        CountryDao countryDao;
        MutableLiveData countriesLiveData;

        selectCountriesAsyncTask(CountryDao dao, MutableLiveData countriesLiveData){
            countryDao = dao;
            this.countriesLiveData = countriesLiveData;
        }

        @Override
        protected List<Country> doInBackground(Void... voids) {
            return countryDao.getCountries();
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            if (countries.size() != 0) {
                countriesLiveData.setValue(new MainResponse(countries, 0, new Throwable("Nema neta ali ima vrednost iz rooma")));
            }else{
                countriesLiveData.setValue(new MainResponse(null, 0, new Throwable("nema neta ni podatke iz rooma")));
            }
            ETravelRoomDatabase.destroyInstance();
        }
    }
}
