package com.example.e_travel.response;

import com.example.e_travel.model.City;

import java.util.ArrayList;

public class CityDescriptionResponse {

    private ArrayList<City> city;
    private Throwable throwable;

    public CityDescriptionResponse(ArrayList<City> city, Throwable throwable) {
        this.city = city;
        this.throwable = throwable;
    }

    public ArrayList<City> getCity() {
        return city;
    }

    public void setCity(ArrayList<City> city) {
        this.city = city;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
