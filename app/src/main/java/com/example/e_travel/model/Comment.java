package com.example.e_travel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("id_user")
    @Expose
    private int userId;

    @SerializedName("time")
    @Expose
    private Timestamp time;

    public Comment(int id, String comment, int userId, Timestamp time) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
