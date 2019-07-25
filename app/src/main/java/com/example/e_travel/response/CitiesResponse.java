package com.example.e_travel.response;

import com.example.e_travel.model.City;

import java.util.List;

public class CitiesResponse {

    private List<City> cityList;
    private Throwable throwable;

    public CitiesResponse(List<City> cityList, Throwable throwable) {
        this.cityList = cityList;
        this.throwable = throwable;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
