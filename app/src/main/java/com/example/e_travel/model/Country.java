package com.example.e_travel.model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;


//    @SerializedName("image_src")
//    @Expose
//    private String imageSrc;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
       // this.imageSrc = imageSrc;     , String imageSrc
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

//    public String getImageSrc() {
//        return imageSrc;
//    }
//
//    public void setImageSrc(String imageSrc) {
//        this.imageSrc = imageSrc;
//    }
}
