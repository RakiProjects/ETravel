package com.example.e_travel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "countries")
public class Country {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = false)
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("src")
    @Expose
    @ColumnInfo(name = "src")
    private String src;

    @SerializedName("alt")
    @Expose
    @ColumnInfo(name = "alt")
    private String alt;

    public Country(int id, String name, String src, String alt) {
        this.id = id;
        this.name = name;
        this.src = src;
        this.alt = alt;
    }

    public Country(Country country) {
        this.id = country.id;
        this.name = country.name;
        this.src = country.src;
        this.alt = country.alt;
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


}
