package com.example.movies.net.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsList {
    @SerializedName("docs")
    private List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }

    public ReviewsList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
