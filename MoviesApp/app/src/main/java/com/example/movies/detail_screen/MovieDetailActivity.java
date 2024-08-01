package com.example.movies.detail_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.net.entities.Movie;
import com.example.movies.net.entities.Review;
import com.example.movies.net.entities.ReviewsList;
import com.example.movies.net.entities.Trailer;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private MovieDetailViewModel viewModel;
    private TrailersAdapter trailersAdapter;
    private ReviewAdapter reviewAdapter;
    private static final String EXTRA_MOVIE = "movie";
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private ImageView imageViewPoster;
    private ImageView imageViewStar;
    private ImageView expandCollapseImage;
    private TextView nameOfFilm;
    private TextView year;
    private TextView description;
    private TextView expandCollapseText;
    private LinearLayout reviewsBlock;
    private LinearLayout teasersBlock;
    private LinearLayout expandCollapseReviews;
    private Boolean isExpand = false;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();

        layoutManager = new GridLayoutManager(this,
                1,
                GridLayoutManager.HORIZONTAL,
                false);

        trailersAdapter = new TrailersAdapter();
        reviewAdapter = new ReviewAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        recyclerViewReviews.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        assert movie != null;
        setMovieDetails(movie);
        observeViewModel(movie);

        setOnTrailerClickListener();
        setOnExpandOrCollapseReviewsClickListener();
    }

    private void setOnExpandOrCollapseReviewsClickListener() {
        expandCollapseReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                if (isExpand) {
                    layoutManager = new LinearLayoutManager(MovieDetailActivity.this,
                            LinearLayoutManager.VERTICAL,
                            false);
                    expandCollapseText.setText(R.string.collapse);
                    expandCollapseImage.setImageDrawable(
                            AppCompatResources.getDrawable(
                                    MovieDetailActivity.this,
                                    R.drawable.arrow_up)
                    );
                    recyclerViewReviews.setLayoutManager(layoutManager);
                } else if (!isExpand){
                    layoutManager = new GridLayoutManager(MovieDetailActivity.this,
                            1,
                            GridLayoutManager.HORIZONTAL,
                            false);
                    expandCollapseText.setText(R.string.expand);
                    expandCollapseImage.setImageDrawable(
                            AppCompatResources.getDrawable(
                                    MovieDetailActivity.this,
                                    R.drawable.arrow_down)
                    );
                    recyclerViewReviews.setLayoutManager(layoutManager);
                }
            }
        });
    }

    private void setOnTrailerClickListener() {
        trailersAdapter.setTrailersOnClickListener(new TrailersAdapter.TrailersOnClickListener() {
            @Override
            public void trailerOnClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void observeViewModel(Movie movie) {

        Drawable starOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on);

        viewModel.getFavouriteMovie(movie.getId())
                .observe(this, new Observer<Movie>() {
                    @Override
                    public void onChanged(Movie movieDb) {
                        if (movieDb == null) {
                            imageViewStar.setImageDrawable(starOff);
                            imageViewStar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewModel.insertMovie(movie);
                                }
                            });

                        } else {
                            imageViewStar.setImageDrawable(starOn);
                            imageViewStar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewModel.removeMovie(movie.getId());
                                }
                            });
                        }
                    }
                });

        viewModel.getMovieTrailer().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                if (!trailers.isEmpty()) {
                    teasersBlock.setVisibility(View.VISIBLE);
                    trailersAdapter.setList(trailers);
                }
            }
        });

        viewModel.getMovieReview().observe(this, new Observer<ReviewsList>() {
            @Override
            public void onChanged(ReviewsList reviews) {
                if (!reviews.getReviewList().isEmpty()) {
                    reviewsBlock.setVisibility(View.VISIBLE);
                    reviewAdapter.setReviewsLists(reviews.getReviewList());
                }
            }
        });

        setOnReviewClickListener();
    }

    private void setOnReviewClickListener() {
        reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClick() {
            @Override
            public void onReviewClick(Review review) {
                Intent intent = ReviewActivity.newIntent(MovieDetailActivity.this, review);
                startActivity(intent);
            }
        });
    }


    private void setMovieDetails(Movie movie) {
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        nameOfFilm.setText(String.format(getString(R.string.s), movie.getName()));
        year.setText(String.valueOf(movie.getYear()));
        description.setText(movie.getDescription());
        viewModel.loadTrailers(movie.getId());
        viewModel.loadReviews(movie.getId());
    }

    private void initViews() {
        reviewsBlock = findViewById(R.id.movieDetailsReviews);
        teasersBlock = findViewById(R.id.movieDetailsTeasers);
        expandCollapseReviews = findViewById(R.id.expandCollapseReviews);
        expandCollapseImage = findViewById(R.id.imageViewExpandCollapse);
        expandCollapseText = findViewById(R.id.textViewExpandCollapse);
        imageViewPoster = findViewById(R.id.poster);
        nameOfFilm = findViewById(R.id.title);
        year = findViewById(R.id.year);
        description = findViewById(R.id.description);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        imageViewStar = findViewById(R.id.imageViewStar);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}