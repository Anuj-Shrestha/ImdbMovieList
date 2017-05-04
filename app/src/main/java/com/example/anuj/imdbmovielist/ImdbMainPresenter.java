package com.example.anuj.imdbmovielist;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anuj on 5/4/17.
 */

public class ImdbMainPresenter {
    private ImdbContract.View imdbView;
    public ImdbMainPresenter() {

    }

    public void setMainActivityView(ImdbContract.View ImdbView) {
        this.imdbView = ImdbView;
    }

    public void onShowSearchBox() {
        imdbView.showSearchBox();
    }

    public void onRemoveSearchBox() {
        imdbView.removeSearchBox();
    }

    public void searchMovies(String searchParam) {
        RetrofitManager.getInstance().getPopularMovies(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {
                imdbView.removeSpinner();

                if (response.code() == 200) {
                    ArrayList<Results> movies = new ArrayList(response.body().getResults());

                    imdbView.setMovieListAdapterData(movies);

                }else{
                    imdbView.displayErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                imdbView.displayErrorMessage("Error has occured");
            }
        }, searchParam);
    }

}
