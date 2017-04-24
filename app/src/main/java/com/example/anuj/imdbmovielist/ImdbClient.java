package com.example.anuj.imdbmovielist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anuj on 4/20/17.
 */

public interface ImdbClient {
    @GET("/")
    Call<MovieResponse> reposForUser(@Query("s") String name);
}
