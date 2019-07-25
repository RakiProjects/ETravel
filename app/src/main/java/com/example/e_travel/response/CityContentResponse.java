package com.example.e_travel.response;

import com.example.e_travel.model.CityContent;

import java.util.List;

public class CityContentResponse {

    private List<CityContent> cityList;
    private Throwable throwable;

    public CityContentResponse(List<CityContent> cityList, Throwable throwable) {
        this.cityList = cityList;
        this.throwable = throwable;
    }

    public List<CityContent> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityContent> cityList) {
        this.cityList = cityList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
