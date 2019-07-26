package com.example.e_travel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("id_country")
    @Expose
    private int idCountry;


    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("area")
    @Expose
    private float area;

    @SerializedName("population")
    @Expose
    private float population;

    @SerializedName("src")
    @Expose
    private String src;

    @SerializedName("alt")
    @Expose
    private String alt;

    public City(int id, String name, int idCountry, String description, float area, float population, String src, String alt) {
        this.id = id;
        this.name = name;
        this.idCountry = idCountry;
        this.description = description;
        this.area = area;
        this.population = population;
        this.src = src;
        this.alt = alt;
    }

    public City(int id, String name, int idCountry) {
        this.id = id;
        this.name = name;
        this.idCountry = idCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getPopulation() {
        return population;
    }

    public void setPopulation(float population) {
        this.population = population;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
