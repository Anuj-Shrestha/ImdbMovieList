package com.example.anuj.imdbmovielist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anuj on 5/5/17.
 */

public class ImdbSearchManager implements ImdbInteractor {
    private SearchMovieCallback searchMovieCallback;

    @Override
    public void searchMovies(String searchQuerry, final SearchMovieCallback searchMovieCallback) {
        this.searchMovieCallback = searchMovieCallback;

        RetrofitManager.getInstance().getPopularMovies(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> movies = new ArrayList(response.body().getResults());
                    searchMovieCallback.onSuccess(movies);


                }else{
                    searchMovieCallback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                searchMovieCallback.onFailure("Error has occured");
            }
        }, searchQuerry);
    }


}
