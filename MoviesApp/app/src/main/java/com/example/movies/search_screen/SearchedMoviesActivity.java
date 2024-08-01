package com.example.movies.search_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.MoviesAdapter;
import com.example.movies.R;
import com.example.movies.detail_screen.MovieDetailActivity;
import com.example.movies.net.entities.Movie;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SearchedMoviesActivity extends AppCompatActivity {
    private MoviesAdapter moviesAdapter;
    private SearchedMoviesViewModel viewModel;
    private RecyclerView recyclerViewSearchedMovies;
    private TextView textViewResult;
    private EditText editTextSearch;
    private Toolbar topAppBar;
    private ProgressBar progressBar;
    private ConstraintLayout parentLayout;
    private static final String EXTRA_SEARCH_QUERY = "search_query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_movies);
        initViews();

        viewModel = new ViewModelProvider(this).get(SearchedMoviesViewModel.class);
        observeViewModel();

        String searchQuery = getIntent().getStringExtra(EXTRA_SEARCH_QUERY);
        editTextSearch.setText(searchQuery);

        recyclerViewSearchedMovies.setLayoutManager(new GridLayoutManager(this, 2));

        moviesAdapter = new MoviesAdapter();
        recyclerViewSearchedMovies.setAdapter(moviesAdapter);

        viewModel.loadSearchedMovies(searchQuery);

        setOnMovieClickListener();
        setOnBackPressClickListener();
    }

    private void setOnBackPressClickListener() {
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchedMoviesActivity.this.finish();
            }
        });
    }

    private void setOnMovieClickListener() {
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(SearchedMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getNetError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    showNetErrorMessage();
                }
            }
        });

        viewModel.getSearchedMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }

                moviesAdapter.setMovies(movies);
                textViewResult.setText(String.format(getString(R.string.result_of_query), movies.size()));
            }
        });
    }

    private void initViews() {
        textViewResult = findViewById(R.id.textViewResult);
        editTextSearch = findViewById(R.id.editTextSearch);
        topAppBar = findViewById(R.id.search_toolbar);
        progressBar = findViewById(R.id.progressBarLoading);
        parentLayout = findViewById(R.id.parentSearchLayout);
        recyclerViewSearchedMovies = findViewById(R.id.recyclerViewSearchedMovies);
    }

    private void showNetErrorMessage() {
        Snackbar snackbar = Snackbar.make(parentLayout,
                R.string.snackbar_message,
                BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
    }

    public static Intent newIntent(Context context, String searchQuery) {
        Intent intent = new Intent(context, SearchedMoviesActivity.class);
        intent.putExtra(EXTRA_SEARCH_QUERY, searchQuery);
        return intent;
    }

}