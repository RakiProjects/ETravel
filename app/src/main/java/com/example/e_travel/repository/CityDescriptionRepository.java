package com.example.e_travel.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.City;
import com.example.e_travel.response.CityDescriptionResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDescriptionRepository extends  BaseRepository {

    private static CityDescriptionRepository cityDescRepository;

    public static CityDescriptionRepository getInstance(){
        if(cityDescRepository == null){
            cityDescRepository = new CityDescriptionRepository();
        }
        return cityDescRepository;
    }

    private CityDescriptionRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCityDesctiption(final MutableLiveData<CityDescriptionResponse> cityDescLiveData, int cityId){

        Call<ArrayList<City>> call = service.getCityDescription(cityId);



        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if(response.isSuccessful()){
                    ArrayList<City> cityDescription = response.body();
                    cityDescLiveData.setValue(new CityDescriptionResponse(cityDescription, null));

                }else{
                    try {
                        response.errorBody().string();
                    } catch (Exception e) {
                        cityDescLiveData.setValue(new CityDescriptionResponse(null, e));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                Log.e("TAG", "error", t);
                cityDescLiveData.setValue(new CityDescriptionResponse(null, t));
            }
        });

    }

}
