package com.example.movies.detail_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.movies.R;
import com.example.movies.net.entities.Review;

public class ReviewActivity extends AppCompatActivity {

    private static final String EXTRA_REVIEW = "review";
    private Toolbar topAppBar;
    private TextView reviewTitle;
    private TextView reviewBody;
    private TextView likes;
    private TextView dislikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initViews();

        Review review = (Review) getIntent().getSerializableExtra(EXTRA_REVIEW);

        assert review != null;
        topAppBar.setTitle(review.getAuthorName());

        if (!review.getTitle().isEmpty()) {
            reviewTitle.setText(review.getTitle());
            reviewTitle.setVisibility(View.VISIBLE);
        }

        reviewBody.setText(review.getReview());
        likes.setText(String.valueOf(review.getReviewLikes()));
        dislikes.setText(String.valueOf(review.getReviewDislikes()));

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewActivity.this.finish();
            }
        });
    }


    private void initViews() {
        topAppBar = findViewById(R.id.review_toolbar);
        reviewTitle = findViewById(R.id.textViewReviewTitle);
        reviewBody = findViewById(R.id.textViewReviewBody);
        likes = findViewById(R.id.textViewLike);
        dislikes = findViewById(R.id.textViewDislike);
    }

    public static Intent newIntent(Context context, Review review) {
     Intent intent = new  Intent(context, ReviewActivity.class);
     intent.putExtra(EXTRA_REVIEW, review);
     return intent;
    }
}