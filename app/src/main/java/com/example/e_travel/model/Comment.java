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
    private String time;

    @SerializedName("name")
    @Expose
    private String userName;

    public Comment(int id, String comment, int userId, String time, String userName) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.time = time;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
