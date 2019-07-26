package com.example.e_travel.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_travel.repository.CityDescriptionRepository;
import com.example.e_travel.response.CityDescriptionResponse;

public class CityDescriptionViewModel extends ViewModel {

    public MutableLiveData<CityDescriptionResponse> cityDescLiveData = new MutableLiveData<>();
    private CityDescriptionRepository cityDescRepository;


    public void getCityDescription(int cityId) {
        if (cityDescLiveData.getValue() != null) {
            return;
        }

        cityDescRepository = CityDescriptionRepository.getInstance();
        cityDescRepository.getCityDesctiption(cityDescLiveData, cityId);
    }
}
