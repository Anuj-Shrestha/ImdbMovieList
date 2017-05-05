package com.example.anuj.imdbmovielist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anuj on 5/5/17.
 */

public class MockImdbInteractor implements ImdbInteractor {
    private SearchMovieCallback searchMovieCallback;

    @Override
    public void searchMovies(String searchQuerry, final SearchMovieCallback searchMovieCallback) {
        this.searchMovieCallback = searchMovieCallback;

        RetrofitManager.getInstance().getPopularMovies(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {
                searchMovieCallback.removeSpinner();

                if (response.code() == 200) {
                    ArrayList<Results> movies = new ArrayList(response.body().getResults());

                    searchMovieCallback.setMovieListAdapterData(movies);

                }else{
                    searchMovieCallback.displayErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                searchMovieCallback.displayErrorMessage("Error has occured");
            }
        }, searchQuerry);
    }


}
