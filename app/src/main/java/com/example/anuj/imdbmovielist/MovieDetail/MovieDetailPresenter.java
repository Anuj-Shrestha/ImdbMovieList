package com.example.anuj.imdbmovielist.MovieDetail;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.anuj.imdbmovielist.Results;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anuj on 5/4/17.
 * Presenter to manage views, business logic and api calls.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View movieDetailView;
    private MovieDetailInteractor movieDetailInteractor;
    private FetchVideoManager.FetchVideosCallback fetchVideosCallback;

    public MovieDetailPresenter(MovieDetailInteractor movieDetailInteractor) {
        this.movieDetailInteractor = movieDetailInteractor;
        this.fetchVideosCallback = new FetchVideosCallbackImplementation();
    }

    public void setMovieDetailView(MovieDetailContract.View movieDetailView) {
        this.movieDetailView = movieDetailView;
    }

    public void showMainPoster(Context context, String imageUrl, ImageView posterImageView) {
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        movieDetailView.removeSpinner();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        movieDetailView.removeSpinner();
                        return false;
                    }
                })
                .into(posterImageView);
    }

    public void showTitlePoster(Context context, String poster, ImageView miniPosterImageView) {
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + poster)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        movieDetailView.removeMiniSpinner();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        movieDetailView.removeMiniSpinner();
                        return false;
                    }
                })
                .into(miniPosterImageView);
    }

    public void applyFormatedReleasedDate(String tmdbReleaseDate) {
        DateFormat getDate = new SimpleDateFormat("yyyy-MM-DD");
        try {
            Date convertedDate = getDate.parse(tmdbReleaseDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yyyy");
            String releasedDate = newFormat.format(convertedDate);
            movieDetailView.applyFormatedReleasedDate(releasedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void fetchVideos(String id, String backDropUri) {
        movieDetailInteractor.fetchVideos(id, backDropUri, fetchVideosCallback);
    }

    /**
     * Implements callbacks fro fetch videos method.
     */
    public class FetchVideosCallbackImplementation implements MovieDetailInteractor.FetchVideosCallback {

        @Override
        public void onSuccess(ArrayList<Results> videos, String backDropUri, String videoId) {
            movieDetailView.setVideosToAdapter(videos, backDropUri);
            movieDetailView.loadVideo(videoId);
        }

        @Override
        public void onFailure(String errorMessage) {
            movieDetailView.showErrorMessage(errorMessage);
        }
    }
}
