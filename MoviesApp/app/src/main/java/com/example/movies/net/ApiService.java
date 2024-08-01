package com.example.movies.net;

import com.example.movies.net.entities.MovieAllInfo;
import com.example.movies.net.entities.MovieResponse;
import com.example.movies.net.entities.ReviewsList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30&token=YGQBDXW-WCSMNT0-HS20VWT-E97FD8R")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie/search?limit=100&token=YGQBDXW-WCSMNT0-HS20VWT-E97FD8R")
    Single<MovieResponse> loadSearchedFilms(@Query("query") String editText);

    @GET("movie/{id}?token=YGQBDXW-WCSMNT0-HS20VWT-E97FD8R")
    Single<MovieAllInfo> loadTrailers(@Path("id") int id);

    @GET("review?token=YGQBDXW-WCSMNT0-HS20VWT-E97FD8R")
    Single<ReviewsList> loadReviews(@Query("movieId") int id);
    @GET("movie?token=38HJ7V3-VGFM9MS-Q3HTWKP-19N95PW")
    Single<MovieResponse> loadProfSearchedMovies(@Query("year") String year,
                                                 @Query("rating.kp") String rating,
                                                 @Query("genres.name") String genres,
                                                 @Query("countries.name") String country,
                                                 @Query("sortField") String sortField,
                                                 @Query("sortType") String sortType
                                                 );
}
