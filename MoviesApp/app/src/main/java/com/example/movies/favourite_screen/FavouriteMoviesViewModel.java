package com.example.movies.favourite_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.local.MovieDao;
import com.example.movies.local.MovieDataBase;
import com.example.movies.net.entities.Movie;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {
    private MovieDao movieDao;
    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDataBase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> getAllFavorMovies(){
        return movieDao.getAllFavouriteMovies();
    }
}
