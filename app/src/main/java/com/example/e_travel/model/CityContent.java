package com.example.e_travel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityContent {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("lat")
    @Expose
    private float lat;

    @SerializedName("lon")
    @Expose
    private float lon;

    @SerializedName("id_city")
    @Expose
    private int id_city;

    @SerializedName("src")
    @Expose
    private String src;

    @SerializedName("alt")
    @Expose
    private String alt;

    @SerializedName("target_type")
    @Expose
    private String targetType;


    public CityContent(int id, String name, String description, float lat, float lon, int id_city, String src, String alt, String targetType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.id_city = id_city;
        this.src = src;
        this.alt = alt;
        this.targetType = targetType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
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

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
