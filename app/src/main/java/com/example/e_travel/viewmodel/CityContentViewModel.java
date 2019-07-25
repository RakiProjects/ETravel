package com.example.e_travel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_travel.repository.CityContentRepository;
import com.example.e_travel.response.CityContentResponse;

public class CityContentViewModel extends ViewModel {
    public MutableLiveData<CityContentResponse> cityContentLiveData = new MutableLiveData<>();
    private CityContentRepository cityContentRepository;

    public void getCityContent(int cityId, String table){
        if(cityContentLiveData.getValue() != null){
            return;
        }

        cityContentRepository = CityContentRepository.getInstance();
        cityContentRepository.getCityContent(cityContentLiveData, cityId, table);
    }
}
