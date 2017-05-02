package com.example.anuj.imdbmovielist;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anuj on 4/28/17.
 */

public class RetrofitManager {
    public static Retrofit retrofit = null;
    public static TmdbClient movieListingService = null;
    public static RetrofitManager retrofitManager = null;
    private static String BASE_URL = "https://api.themoviedb.org/3/";

    private RetrofitManager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        movieListingService = retrofit.create(TmdbClient.class);
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    public static void getPopularMovies(Callback<TmdbResponse> getMovieListingCallBack, String searchParam) {

        Call<TmdbResponse> getMovieListing = movieListingService.tmdbPopularMovies(searchParam);
        getMovieListing.enqueue(getMovieListingCallBack);

    }

    public static void getMovieDetail(Callback<TmdbResponse> getMovieDetailCallback, String selectedId) {

        Call<TmdbResponse> getSelectedMovieDetail = movieListingService.tmdbSelectedMovie(selectedId);
        getSelectedMovieDetail.enqueue(getMovieDetailCallback);

    }

}
