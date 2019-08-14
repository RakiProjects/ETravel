package com.example.e_travel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.repository.MainRepository;
import com.example.e_travel.response.MainResponse;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<MainResponse> countriesLiveData = new MutableLiveData<>();
    private MainRepository mainRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCountryList() {
        if (countriesLiveData.getValue() != null) {
            return;
        }

        mainRepository = MainRepository.getInstance(getApplication());
        mainRepository.getCountryList(countriesLiveData);
    }
}
