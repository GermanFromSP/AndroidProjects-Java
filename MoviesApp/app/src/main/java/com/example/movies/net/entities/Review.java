package com.example.movies.net.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {
    @SerializedName("author")
    private String authorName;
    @SerializedName("type")
    private String type;
    @SerializedName("review")
    private String review;
    @SerializedName("title")
    private String title;
    @SerializedName("reviewLikes")
    private int reviewLikes;
    @SerializedName("reviewDislikes")
    private int reviewDislikes;

    public Review(String authorName, String type, String review, String title, int reviewLikes, int reviewDislikes)  {
        this.authorName = authorName;
        this.type = type;
        this.review = review;
        this.title = title;
        this.reviewLikes = reviewLikes;
        this.reviewDislikes = reviewDislikes;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getType() {
        return type;
    }

    public String getReview() {
        return review;
    }

    public String getTitle() {
        return title;
    }

    public int getReviewLikes() {
        return reviewLikes;
    }

    public int getReviewDislikes() {
        return reviewDislikes;
    }

}
