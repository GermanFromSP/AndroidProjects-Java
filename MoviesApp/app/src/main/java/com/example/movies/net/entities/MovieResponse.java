package com.example.movies.net.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("docs")
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public MovieResponse(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieList=" + movieList +
                '}';
    }
}
