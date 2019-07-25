package com.example.e_travel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.User;
import com.example.e_travel.repository.LoginRepository;
import com.example.e_travel.response.UserResponse;

public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<UserResponse> userLiveData = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String email, String password){
        loginRepository = LoginRepository.getInstance();
        loginRepository.login(userLiveData, email, password);
    }
}
