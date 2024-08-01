package com.example.movies.home_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.movies.MovieListViewModel;
import com.example.movies.MoviesAdapter;
import com.example.movies.R;
import com.example.movies.search_screen.SearchedMoviesActivity;
import com.example.movies.detail_screen.MovieDetailActivity;
import com.example.movies.favourite_screen.FavouriteMoviesActivity;
import com.example.movies.favourite_screen.FavouriteMoviesViewModel;
import com.example.movies.net.entities.Movie;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private TodayMoviesAdapter todayMoviesAdapter;
    private TopMoviesAdapter topMoviesAdapter;
    private FavouriteMoviesViewModel viewModel;
    private MovieListViewModel todaySelectionViewModel;
    private RecyclerView recyclerViewFavour;
    private RecyclerView recyclerViewToday;
    private Button buttonSearch;
    private Button buttonFavour;
    private EditText editText;
    private ProgressBar loadingProgressBar;
    private LinearLayout homeLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        todaySelectionViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        observeViewModel();

        recyclerViewFavour.setLayoutManager(new LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false
                )
        );
        recyclerViewToday.setLayoutManager(new LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false
                )
        );

        todayMoviesAdapter = new TodayMoviesAdapter();
        topMoviesAdapter = new TopMoviesAdapter();

        recyclerViewFavour.setAdapter(topMoviesAdapter);

        todaySelectionViewModel.loadMovies();

        recyclerViewToday.setAdapter(todayMoviesAdapter);

        setOnFavouriteClickListener();
        setOnSelectionClickListener();
        setOnFavouriteButtonClickListener();
        setOnSearchButtonClickListener();
    }

    private void observeViewModel() {
        todaySelectionViewModel.getNetError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    showNetErrorMessage();
                }
            }
        });

        todaySelectionViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies.isEmpty()) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                } else {
                    loadingProgressBar.setVisibility(View.GONE);
                    todayMoviesAdapter.setMovies(movies);
                }

            }
        });

        viewModel.getAllFavorMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        topMoviesAdapter.setMovies(movies);
                    }
                }
        );
    }

    private void showNetErrorMessage() {
        Snackbar snackbar = Snackbar.make(homeLinearLayout,
                R.string.snackbar_message,
                BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
    }

    private void setOnFavouriteButtonClickListener() {
        buttonFavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FavouriteMoviesActivity.newIntent(HomePageActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setOnSearchButtonClickListener() {
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchedMoviesActivity.newIntent(
                        HomePageActivity.this,
                        editText.getText().toString()
                );
                startActivity(intent);
            }
        });
    }

    private void setOnFavouriteClickListener() {
        topMoviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                goToDetails(movie);
            }
        });
    }

    private void setOnSelectionClickListener() {
        todayMoviesAdapter.setOnMovieClickListener(new TodayMoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                goToDetails(movie);
            }
        });
    }

    private void goToDetails(Movie movie) {
        Intent intent = MovieDetailActivity.newIntent(HomePageActivity.this, movie);
        startActivity(intent);
    }

    private void initViews() {
        editText = findViewById(R.id.editTextSearch);
        homeLinearLayout = findViewById(R.id.menuLinearLayout);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerViewFavour = findViewById(R.id.recyclerViewMoviesFavour);
        recyclerViewToday = findViewById(R.id.recyclerViewMoviesToday);
        buttonFavour = findViewById(R.id.buttonFavour);
        loadingProgressBar = findViewById(R.id.progressBarLoading);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomePageActivity.class);
    }
}