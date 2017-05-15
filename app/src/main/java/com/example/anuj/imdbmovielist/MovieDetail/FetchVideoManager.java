package com.example.anuj.imdbmovielist.MovieDetail;

import android.util.Log;

import com.example.anuj.imdbmovielist.Results;
import com.example.anuj.imdbmovielist.RetrofitManager;
import com.example.anuj.imdbmovielist.TmdbResponse;
import com.example.anuj.imdbmovielist.di.DaggerApplicationComponent;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anuj on 5/8/17.
 * Manager that calls video list api and sends back data to presenter.
 */

public class FetchVideoManager implements MovieDetailInteractor {

    private FetchVideosCallback fetchVideosCallback;

    RetrofitManager retrofitManager;

    public FetchVideoManager(RetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }

    @Override
    public void fetchVideos(String id, final String backDropUri, final FetchVideosCallback fetchVideosCallback) {
        this.fetchVideosCallback = fetchVideosCallback;

        retrofitManager.getMovieDetail(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> vidoes = new ArrayList(response.body().getResults());
                    String videoId = vidoes.get(0).getKey();
                    fetchVideosCallback.onSuccess(vidoes, backDropUri, videoId);

                } else {
                    fetchVideosCallback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                fetchVideosCallback.onFailure("Unable to fetch videos");
                Log.e("Exception found ", t.getLocalizedMessage());
            }
        }, id);
    }
}
