package com.example.anuj.imdbmovielist.MovieDetail;


import com.example.anuj.imdbmovielist.Results;

import java.util.ArrayList;

/**
 * Created by anuj on 5/8/17.
 */

public interface MovieDetailInteractor {
    void fetchVideos (String id, String backDropUri, FetchVideosCallback fetchVideosCallback);

    interface FetchVideosCallback {
        void onSuccess(ArrayList<Results> videos, String backDropUri, String videoId);
        void onFailure(String errorMessage);
    }
}
