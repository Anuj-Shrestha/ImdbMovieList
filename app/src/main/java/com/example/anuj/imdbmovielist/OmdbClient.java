package com.example.anuj.imdbmovielist;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anuj on 4/20/17.
 */

public interface OmdbClient {
    @GET("/")
    Call<MovieResponse> reposForUser(@Query("s") String name);

    @GET("/")
    Call<Search> selectedMovie(@Query("i") String name);
}
