package com.example.e_travel.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.model.User;
import com.example.e_travel.response.UserResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository extends BaseRepository {

    private static LoginRepository loginRepository;

    public static LoginRepository getInstance(){
        if(loginRepository ==null){
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }

    private LoginRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void login(final MutableLiveData<UserResponse> userLiveData, String email, String password){
        Call<User> call = service.login(email, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user.getError().equals("")){
                        userLiveData.setValue(new UserResponse(user, null, ""));
                    }
                    if(user.getError().equals("error")){
                        userLiveData.setValue(new UserResponse(null, null, "error"));
                    }
                }else{
                    userLiveData.setValue(new UserResponse(null, null, "serverError"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userLiveData.setValue(new UserResponse(null, t, "onFailure"));
            }
        });

    }
}
