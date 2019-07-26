package com.example.e_travel.retrofit;

import com.example.e_travel.model.City;
import com.example.e_travel.model.CityContent;
import com.example.e_travel.model.Comment;
import com.example.e_travel.model.Country;
import com.example.e_travel.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApi {

    // countryList
    @GET("countries.php")
    Call<ArrayList<Country>> getCountryList();

    @GET("cities.php")
    Call<ArrayList<City>> getCityList(@Query("idCountry") int idCountry);

    @GET("city_content.php")
    Call<ArrayList<CityContent>> getCityContent(@Query("cityId") int cityId, @Query("table") String table);

    //@GET("register.php")
    // Call<User> register(@Query("email") String email, @Query("password") String password, @Query("name") String name);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> register(@Field("email") String email, @Field("password") String password, @Field("name") String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(@Field("email") String email, @Field("password") String password);

    @GET("cityDescription.php")
    Call<ArrayList<City>> getCityDescription(@Query("cityId") int cityId);


    @GET("commentInsert.php")
    Call<ArrayList<Comment>> insertComment(@Query("comment") String comment, @Query("userId") int userId, @Query("placeId") int placeId, @Query("placeType") String placeType);

    @GET("comments.php")
    Call<ArrayList<Comment>> getCommentList(@Query("placeId") int placeId, @Query("placeType") String placeType);
}
