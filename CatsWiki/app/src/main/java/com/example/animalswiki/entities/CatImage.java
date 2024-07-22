package com.example.animalswiki.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatImage {
    @SerializedName("url")
   private String url;

    public CatImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "CatImage{" +
                "url='" + url + '\'' +
                '}';
    }
}

