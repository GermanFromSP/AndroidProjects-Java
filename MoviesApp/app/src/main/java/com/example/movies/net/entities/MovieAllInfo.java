package com.example.movies.net.entities;

import com.google.gson.annotations.SerializedName;

public class MovieAllInfo {
    @SerializedName("videos")
    private Videos videos;

    public MovieAllInfo(Videos videos) {
        this.videos = videos;
    }

    public Videos getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "MovieAllInfo{" +
                "videos=" + videos +
                '}';
    }
}
