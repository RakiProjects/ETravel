package com.example.e_travel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cities")
public class City {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("id_country")
    @Expose
    @ColumnInfo(name = "id_country")
    private int idCountry;

    @SerializedName("description")
    @Expose
    @Ignore
    private String description;

    @SerializedName("area")
    @Expose
    @Ignore
    private float area;

    @SerializedName("population")
    @Expose
    @Ignore
    private float population;

    @SerializedName("src")
    @Expose
    @Ignore
    private String src;

    @SerializedName("alt")
    @Expose
    @Ignore
    private String alt;

    public City(City city) {
        this.id = city.id;
        this.name = city.name;
        this.idCountry = city.idCountry;
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
