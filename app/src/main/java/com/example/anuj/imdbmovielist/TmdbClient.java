package com.example.anuj.imdbmovielist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anuj on 4/20/17.
 */

public interface TmdbClient {
    @GET("movie/{category}?api_key=419cfaa5f394ab7893b5ff4a074e3d83")
    Call<TmdbResponse> tmdbPopularMovies(@Path("category") String category);

    @GET("movie/{id}/videos?api_key=419cfaa5f394ab7893b5ff4a074e3d83")
    Call<TmdbResponse> tmdbSelectedMovie(@Path("id") String id);
}
