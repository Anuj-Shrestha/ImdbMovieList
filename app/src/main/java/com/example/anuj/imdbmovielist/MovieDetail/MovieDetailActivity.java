package com.example.anuj.imdbmovielist.MovieDetail;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anuj.imdbmovielist.ImdbMovieListApplication;
import com.example.anuj.imdbmovielist.R;
import com.example.anuj.imdbmovielist.Results;
import com.example.anuj.imdbmovielist.di.DaggerApplicationComponent;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Displays movie detail and youtube trailers.
 */
public class MovieDetailActivity extends YouTubeBaseActivity implements MovieDetailContract.View, YouTubePlayer.OnInitializedListener, VideoListAdapter.ItemClickListener {
    TextView titleTextView, movieTypeTextView, yearTextView, imdbIdTextView;
    ImageView posterImageView, miniPosterImageView;
    RelativeLayout spinnerRelativeLayout, miniPosterSpinnerRL;
    RecyclerView recyclerView;
    ArrayList<Results> myVideos;
    VideoListAdapter videoListAdapter;
    String backDropUri;
    String videoId;
    YouTubePlayer myYoutubePlayer;
    RatingBar mRatingBar;

    public static final String DEVELOPER_KEY = "AIzaSyCXki-uq3pciNSAUPiuxpXhRrXvvrV-S2o";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment myYouTubePlayerFragment;

//    private MovieDetailContract.Presenter movieDetailPresenter;
    @Inject
    MovieDetailContract.Presenter movieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        movieTypeTextView = (TextView) findViewById(R.id.textview_type);
        yearTextView = (TextView) findViewById(R.id.textview_year);
        imdbIdTextView = (TextView) findViewById(R.id.textview_imdbid);
        posterImageView = (ImageView) findViewById(R.id.imageview_poster);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar_movieRating);
        miniPosterImageView = (ImageView) findViewById(R.id.imageview_miniPoster);
        spinnerRelativeLayout = (RelativeLayout) findViewById(R.id.loadingPanel_poster);
        miniPosterSpinnerRL = (RelativeLayout) findViewById(R.id.loadingPanel_miniposter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_videoList);
        myVideos = new ArrayList<>();

        myYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_youtubePlayer);

        recyclerView.setLayoutManager(new GridLayoutManager(MovieDetailActivity.this, 2));
        videoListAdapter = new VideoListAdapter(MovieDetailActivity.this, myVideos, backDropUri);
        recyclerView.setAdapter(videoListAdapter);
        videoListAdapter.setClickListener(this);

//        movieDetailPresenter = new MovieDetailPresenter(new FetchVideoManager());
        ((ImdbMovieListApplication)getApplication()).getApplicationComponent().inject(this);
        movieDetailPresenter.setMovieDetailView(this);

        Results clickedMovie = getIntent().getParcelableExtra("MovieDetailActivity");

        movieDetailPresenter.showMainPoster(this, clickedMovie.getBackdrop(), posterImageView);

        titleTextView.setText(clickedMovie.getTitle());

        movieDetailPresenter.showTitlePoster(this, clickedMovie.getPoster(), miniPosterImageView);
       
        movieDetailPresenter.applyFormatedReleasedDate(clickedMovie.getYear());

        movieTypeTextView.setText(clickedMovie.getOverview());
        imdbIdTextView.setText(getString(R.string.id) + clickedMovie.getId());
        mRatingBar.setRating(Float.parseFloat(clickedMovie.getVote()));

        movieDetailPresenter.fetchVideos(clickedMovie.getId(), clickedMovie.getBackdrop());

    }

    @Override
    public void onItemClick(View view, int position) {
        videoId = videoListAdapter.getItem(position).getKey();
        if (myYoutubePlayer == null) {
            myYouTubePlayerFragment.initialize(DEVELOPER_KEY, MovieDetailActivity.this);

        } else {
            myYoutubePlayer.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            myYoutubePlayer = youTubePlayer;
            String newVideoId = getVideoId();

            myYoutubePlayer.cueVideo(newVideoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.youtubeInitialize_fail),
                    youTubeInitializationResult.toString());
            showErrorMessage(errorMessage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.fragment_youtubePlayer);
    }

    public String getVideoId() {
        return videoId;
    }

    @Override
    public void removeSpinner() {
        spinnerRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void removeMiniSpinner() {
        miniPosterSpinnerRL.setVisibility(View.GONE);
    }

    @Override
    public void applyFormatedReleasedDate(String releasedDate) {
        yearTextView.setText(getString(R.string.releasedDate) + releasedDate);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(MovieDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setVideosToAdapter(ArrayList<Results> vidoes, String backDropUri) {
        videoListAdapter.setValues(vidoes, backDropUri);
    }

    @Override
    public void loadVideo(String videoId) {
        if (myYoutubePlayer == null) {
            this.videoId = videoId;
            myYouTubePlayerFragment.initialize(DEVELOPER_KEY, MovieDetailActivity.this);
        } else {
            myYoutubePlayer.cueVideo(videoId);
        }
    }
}
