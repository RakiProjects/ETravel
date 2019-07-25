package com.example.e_travel.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://10.0.2.2/ETravel/";       // 10.20.130.71

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }
}