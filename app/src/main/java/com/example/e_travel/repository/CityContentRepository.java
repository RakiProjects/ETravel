package com.example.e_travel.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.CityContent;
import com.example.e_travel.response.CityContentResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityContentRepository extends BaseRepository {

    private static final String TAG = CityContentRepository.class.getSimpleName();

    private static CityContentRepository cityContentRepository;

    public static CityContentRepository getInstance(){
        if(cityContentRepository == null){
            cityContentRepository = new CityContentRepository();
        }
        return cityContentRepository;
    }

    private CityContentRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void getCityContent(final MutableLiveData cityContentLiveData, int cityId, String table){

        Call<ArrayList<CityContent>> call = service.getCityContent(cityId, table);
        Log.v(TAG, "call Url=" +call.request().url());

        call.enqueue(new Callback<ArrayList<CityContent>>() {
            @Override
            public void onResponse(Call<ArrayList<CityContent>> call, Response<ArrayList<CityContent>> response) {
                if(response.isSuccessful()){
                    ArrayList<CityContent> cityContent = response.body();
                    cityContentLiveData.setValue(new CityContentResponse(cityContent, null));
                }else{
                    try {
                        response.errorBody().string();
                    } catch (IOException e) {
                        cityContentLiveData.setValue(new CityContentResponse(null, e));
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<CityContent>> call, Throwable t) {
                cityContentLiveData.setValue(new CityContentResponse(null, t));
            }
        });
    }
}
