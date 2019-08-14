package com.example.e_travel.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.CitiesActivity;
import com.example.e_travel.model.City;
import com.example.e_travel.response.CitiesResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;
import com.example.e_travel.room.CityDao;
import com.example.e_travel.room.ETravelRoomDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesRepository extends BaseRepository {

    private static final String TAG = CitiesRepository.class.getSimpleName();

    private static CitiesRepository citiesRepository;

    ETravelRoomDatabase database;
    CityDao cityDao;

    public static CitiesRepository getInstance(Context context) {
        if (citiesRepository == null) {
            citiesRepository = new CitiesRepository(context);
        }
        return citiesRepository;
    }

    private CitiesRepository(Context context) {
        service = RetrofitInstance.createService(WebApi.class);
        database = ETravelRoomDatabase.getInstance(context);
        cityDao = database.cityDao();
    }

    public void getCityList(final MutableLiveData<CitiesResponse> citiesLiveData, final int countryId) {
        Call<ArrayList<City>> call = service.getCityList(countryId);

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    ArrayList<City> cityList = response.body();
                    insertCities(cityList, cityDao);
                    citiesLiveData.setValue(new CitiesResponse(cityList, null));
                } else {
                    try {
                        response.errorBody().string();
                    } catch (IOException e) {
                        citiesLiveData.setValue(new CitiesResponse(null, e));
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                // no internet, room database
                selectCitiesFromRoom(cityDao, citiesLiveData, countryId);
            }
        });
    }

    public void insertCities(List<City> cities, CityDao cityDao){
        new insertCitiesAsyncTask(cityDao).execute(cities);
    }

    private static class insertCitiesAsyncTask extends AsyncTask<List<City>, Void, Void>{

        private CityDao cityDao;

        insertCitiesAsyncTask(CityDao dao){
            cityDao = dao;
        }

        @Override
        protected Void doInBackground(List<City>... lists) {
            cityDao.insertCities(lists[0]);
            return null;
        }
    }

    public void selectCitiesFromRoom(CityDao cityDao, MutableLiveData<CitiesResponse> citiesLiveData, int countryId){
        new selectCitiesAsyncTask(cityDao, citiesLiveData, countryId).execute();
    }

    private static class selectCitiesAsyncTask extends AsyncTask<Void, Void, List<City>>{
        CityDao cityDao;
        MutableLiveData<CitiesResponse> citiesLiveData;
        int countryId;

        public selectCitiesAsyncTask(CityDao cityDao, MutableLiveData<CitiesResponse> citiesLiveData, int countryId) {
            this.cityDao = cityDao;
            this.citiesLiveData = citiesLiveData;
            this.countryId = countryId;
        }

        @Override
        protected List<City> doInBackground(Void... voids) {
            return cityDao.getCities(countryId);
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            if (cities.size() != 0) {
                citiesLiveData.setValue(new CitiesResponse(cities, new Throwable("cities from room database")));
            } else {
                citiesLiveData.setValue(new CitiesResponse(null, new Throwable("no internet and no room data")));
            }
            ETravelRoomDatabase.destroyInstance();
        }
    }
}
