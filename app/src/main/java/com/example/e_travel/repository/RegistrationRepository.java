package com.example.e_travel.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.User;
import com.example.e_travel.response.RegistrationResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRepository extends BaseRepository {

    private static final String TAG = RegistrationRepository.class.getSimpleName();

    private static RegistrationRepository regRepository;

    public static RegistrationRepository getInstance(){
        if(regRepository == null){
            regRepository = new RegistrationRepository();
        }
        return regRepository;
    }

    private RegistrationRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void register(final MutableLiveData<RegistrationResponse> regLiveData, String email,String password, String name){

        Call<User> call = service.register(email, password, name);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user.getError().equals("error")){
                        regLiveData.setValue(new RegistrationResponse(null, "badEmail"));
                    }else{
                        regLiveData.setValue(new RegistrationResponse(null, ""));
                    }
                }else{
                    regLiveData.setValue(new RegistrationResponse( null,"noResponse"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                regLiveData.setValue(new RegistrationResponse(t, "error"));
            }
        });
    }
}
