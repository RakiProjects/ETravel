package com.example.e_travel.response;

import com.example.e_travel.model.User;

public class UserResponse {

    private User user;
    private Throwable throwable;
    private String error;

    public UserResponse(User user, Throwable throwable, String error) {
        this.user = user;
        this.throwable = throwable;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
