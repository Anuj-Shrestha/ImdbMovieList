package com.example.anuj.imdbmovielist.MovieDetail;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.anuj.imdbmovielist.Results;
import com.example.anuj.imdbmovielist.RetrofitManager;
import com.example.anuj.imdbmovielist.TmdbResponse;
import com.google.android.youtube.player.YouTubePlayer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anuj on 5/4/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View movieDetailView;
    public MovieDetailPresenter ( ){
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

    public void fetchMovieDetail(String id, final String backDropUri) {
        RetrofitManager.getInstance().getMovieDetail(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> vidoes = new ArrayList(response.body().getResults());
                    String videoId = vidoes.get(0).getKey();
                    if(!movieDetailView.checkValidYoutubePlayer()) {
                        movieDetailView.loadYoutubeVideo();
                    } else {
                        movieDetailView.cueYoutubeVideo(videoId);
                    }
                    movieDetailView.setVideosToAdapter(vidoes, backDropUri);

                } else {
                    movieDetailView.showErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                movieDetailView.showErrorMessage("error has occured ");
            }
        }, id);
    }
}
