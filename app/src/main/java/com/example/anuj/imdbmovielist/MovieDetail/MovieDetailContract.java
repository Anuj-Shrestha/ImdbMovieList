package com.example.anuj.imdbmovielist.MovieDetail;

import com.example.anuj.imdbmovielist.Results;

import java.util.ArrayList;

/**
 * Created by anuj on 5/4/17.
 */

public interface MovieDetailContract {
    void removeSpinner();

    void removeMiniSpinner();

    void applyFormatedReleasedDate(String releasedDate);

    void loadYoutubeVideo();

    void setVideosToAdapter(ArrayList<Results> videos, String backDropUri);

    void showErrorMessage(String errorMessage);

    void cueYoutubeVideo(String videoId);
//    public interface View {
////        setMovieDetailView(MovieDetailContract movieDetailContract);
//
//    }
}
