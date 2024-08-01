package com.example.movies.detail_screen;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.local.MovieDao;
import com.example.movies.local.MovieDataBase;
import com.example.movies.net.ApiFactory;
import com.example.movies.net.entities.Movie;
import com.example.movies.net.entities.MovieAllInfo;
import com.example.movies.net.entities.ReviewsList;
import com.example.movies.net.entities.Trailer;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    private MovieDao movieDao;
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDataBase.getInstance(application).movieDao();
    }
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Trailer>> movieTrailer = new MutableLiveData<>();
    private MutableLiveData<ReviewsList> movieReview = new MutableLiveData<>();

    public LiveData<ReviewsList> getMovieReview() {
        return movieReview;
    }

    public LiveData<List<Trailer>> getMovieTrailer() {
        return movieTrailer;
    }
    public LiveData<Movie> getFavouriteMovie(int movieId){
        return movieDao.getFavouriteMovie(movieId);
    }

    public void insertMovie(Movie movie){
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }
    public void removeMovie(int movieId){
        Disposable disposable = movieDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
        }


    public void loadTrailers (int id){
       Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .map(new Function<MovieAllInfo, List<Trailer>>() {
                   @Override
                   public List<Trailer> apply(MovieAllInfo movieAllInfo) throws Throwable {
                       return movieAllInfo.getVideos().getTrailers();
                   }
               })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        movieTrailer.setValue(trailerList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("MovieDA", throwable.toString());
                    }
                });
       compositeDisposable.add(disposable);
    }

    public void loadReviews(int id){
        Disposable disposable = ApiFactory.apiService.loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ReviewsList>() {
                            @Override
                            public void accept(ReviewsList reviews) throws Throwable {
                               movieReview.setValue(reviews);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        });

                        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
