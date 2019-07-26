package com.example.e_travel.response;

import com.example.e_travel.model.Comment;

import java.util.ArrayList;

public class CommentsResponse {

    private ArrayList<Comment> commentList;
    private Throwable throwable;

    public CommentsResponse(ArrayList<Comment> commentList, Throwable throwable) {
        this.commentList = commentList;
        this.throwable = throwable;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
