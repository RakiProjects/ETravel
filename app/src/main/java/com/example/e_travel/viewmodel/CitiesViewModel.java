package com.example.e_travel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.repository.CitiesRepository;
import com.example.e_travel.response.CitiesResponse;

public class CitiesViewModel extends AndroidViewModel {

    public MutableLiveData<CitiesResponse> citiesLiveData = new MutableLiveData<>();
    private CitiesRepository citiesRepository;

    public CitiesViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCityList(int countryId){
        if(citiesLiveData.getValue() != null){
            return;
        }
        citiesRepository = CitiesRepository.getInstance(getApplication());
        citiesRepository.getCityList(citiesLiveData, countryId);
    }
}
