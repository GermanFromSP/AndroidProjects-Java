package com.example.animalswiki.net;

import com.example.animalswiki.entities.Cat;
import com.example.animalswiki.entities.CatImage;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
@GET("breeds")
Single<List<Cat>> allCats();

@GET("images/search?&limit=10&api_key=REPLACE_ME")
Single<List<CatImage>> catImage(@Query("breed_ids") String id);
}
