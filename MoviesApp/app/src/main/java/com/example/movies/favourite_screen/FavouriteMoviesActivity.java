package com.example.movies.favourite_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.MoviesAdapter;
import com.example.movies.R;
import com.example.movies.detail_screen.MovieDetailActivity;
import com.example.movies.net.entities.Movie;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        TextView textViewFavour = findViewById(R.id.textViewFavour);

        MoviesAdapter moviesAdapter = new MoviesAdapter();
        RecyclerView recyclerViewFavor = findViewById(R.id.recyclerViewFavorMovies);
        Toolbar topAppBar = findViewById(R.id.favourite_toolbar);

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouriteMoviesActivity.this.finish();
            }
        });

        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this)
                .get(FavouriteMoviesViewModel.class);
        viewModel.getAllFavorMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> moviesDb) {
                moviesAdapter.setMovies(moviesDb);
                textViewFavour.setText(String.format(getString(R.string.your_favourite_movies), moviesDb.size()));
            }
        });
        recyclerViewFavor.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewFavor.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }

}