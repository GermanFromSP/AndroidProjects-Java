package com.example.movies.search_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.net.ApiFactory;
import com.example.movies.net.entities.Movie;
import com.example.movies.net.entities.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchedMoviesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Movie>> searchedMovies = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> netError = new MutableLiveData<>();

    public MutableLiveData<Boolean> getNetError() {
        return netError;
    }

    public MutableLiveData<List<Movie>> getSearchedMovies() {
        return searchedMovies;
    }

    public SearchedMoviesViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadSearchedMovies(String editName) {
        Disposable disposable = ApiFactory.apiService.loadSearchedFilms(editName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        netError.setValue(false);
                        searchedMovies.setValue(movieResponse.getMovieList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        netError.setValue(true);
                    }
                });
        compositeDisposable.add(disposable);
    }

}
