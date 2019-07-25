package com.example.e_travel.response;

public class RegistrationResponse {

    private Throwable throwable;
    private String error;

    public RegistrationResponse(Throwable throwable, String error) {
        this.throwable = throwable;
        this.error = error;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
