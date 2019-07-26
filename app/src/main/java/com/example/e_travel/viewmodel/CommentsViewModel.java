package com.example.e_travel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_travel.repository.CommentsRepository;
import com.example.e_travel.response.CommentsResponse;

public class CommentsViewModel extends ViewModel {

    public MutableLiveData<CommentsResponse> commentsLiveData = new MutableLiveData<>();
    private CommentsRepository commentsRepository;


    public void insertComment(String comment, int userId, int placeId, String placeType) {

        commentsRepository = CommentsRepository.getInstance();
        commentsRepository.insertComment(commentsLiveData, comment, userId, placeId, placeType);
    }

    public void getCommentList(int placeId, String placeType) {

        commentsRepository = CommentsRepository.getInstance();
        commentsRepository.getCommentList(commentsLiveData, placeId, placeType);
    }
}
