package com.example.e_travel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.User;
import com.example.e_travel.repository.RegistrationRepository;
import com.example.e_travel.response.RegistrationResponse;

public class RegistrationViewModel extends AndroidViewModel {

    public MutableLiveData<RegistrationResponse> regLiveData = new MutableLiveData<>();
    private RegistrationRepository regRepository;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(String email, String password, String name) {
        regRepository = RegistrationRepository.getInstance();
        regRepository.register(regLiveData, email, password, name);
    }
}
