package com.example.e_travel.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_travel.response.CommentsResponse;
import com.example.e_travel.retrofit.RetrofitInstance;
import com.example.e_travel.retrofit.WebApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository extends BaseRepository {

    private static final String TAG = CommentsRepository.class.getSimpleName();

    private static CommentsRepository commentsRepository;

    public static CommentsRepository getInstance(){
        if(commentsRepository == null){
            commentsRepository = new CommentsRepository();
        }
        return commentsRepository;
    }

    private CommentsRepository(){
        service = RetrofitInstance.createService(WebApi.class);
    }

    public void insertComment(final MutableLiveData<CommentsResponse> commentsLiveData, String comment, int userId, int placeId, String placeType){
        Call call = service.insertComment(comment, userId, placeId, placeType);

        Log.v(TAG, String.valueOf(call.request().url()));

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Log.v(TAG, " uspelo");
                }else{
                    try {
                        response.errorBody().string();
                    } catch (Exception e) {
                        Log.e(TAG, "catch ", e);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });
    }

    public void getCommentList(final MutableLiveData<CommentsResponse> commentsLiveData, int placeId, String placeType){

    }
}
