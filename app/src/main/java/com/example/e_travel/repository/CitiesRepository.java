package com.example.e_travel.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.City;
import com.example.e_travel.response.CitiesResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesRepository extends BaseRepository {

    private static final String TAG = CitiesRepository.class.getSimpleName();

    private static CitiesRepository citiesRepository;

    public static CitiesRepository getInstance(){
        if(citiesRepository == null){
            citiesRepository = new CitiesRepository();
        }
        return citiesRepository;
    }

    private CitiesRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCityList(final MutableLiveData<CitiesResponse> citiesLiveData, int idCity){
        Call<ArrayList<City>> call = service.getCityList(idCity);

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if(response.isSuccessful()){
                    ArrayList<City> cityList = response.body();
                    Log.v(TAG, response.code()+"");
                    Log.d(TAG, response.raw().toString());
                    citiesLiveData.setValue(new CitiesResponse(cityList, null));
                }else{
                    Log.v(TAG, String.valueOf(response.code()));
                    //...
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                Log.e(TAG, "error ", t);
            }
        });
    }
}
