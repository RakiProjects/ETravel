package com.example.e_travel.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.Country;
import com.example.e_travel.response.MainResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class MainRepository extends BaseRepository{

    private static final String TAG = MainRepository.class.getSimpleName();

    private static MainRepository mainRepository;

    public static MainRepository getInstance(){
        if(mainRepository == null){
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }

    private MainRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCountryList(final MutableLiveData countriesLiveData){

        Call<ArrayList<Country>> call = service.getCountryList();

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                if(response.isSuccessful()){
                   // if(response.code() == 200){ // switch ako vise
                    Log.v(TAG, response.code()+"");
                    Log.d(TAG, response.raw().toString());
                    ArrayList<Country> countryList = response.body();
                    countriesLiveData.setValue(new MainResponse(countryList, response.code(), null));
                    //}
                }else {
                    Log.v(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                Log.e(TAG, "error ", t);
            }
        });

    }
}
