package com.example.e_travel.response;

import com.example.e_travel.model.Country;

import java.util.List;

public class MainResponse {

    private List<Country> countryList;
    private int statusCode;
    private Throwable throwable;

    public MainResponse(List<Country> countryList, int statusCode, Throwable throwable) {
        this.countryList = countryList;
        this.statusCode = statusCode;
        this.throwable = throwable;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
