package com.example.anuj.imdbmovielist;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, VideoListAdapter.ItemClickListener {
    TextView titleTextView, movieTypeTextView, yearTextView, imdbIdTextView, imageUriTextView;
    ImageView posterImageView, miniPosterImageView;
    RelativeLayout spinnerRelativeLayout, miniPosterSpinnerRL;
    RecyclerView recyclerView;
    ArrayList<Results> myVideos;
    VideoListAdapter videoListAdapter;
    VideoView trailerVideoView;
    String backDropUri;
    String firstVideoSrc;
    String videoId;
    YouTubePlayer myYoutubePlayer;

    public static final String DEVELOPER_KEY = "AIzaSyCXki-uq3pciNSAUPiuxpXhRrXvvrV-S2o";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static final String VIDEO_ID = "fhWaJi1Hsfo";

    YouTubePlayerFragment myYouTubePlayerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        movieTypeTextView = (TextView) findViewById(R.id.textview_type);
        yearTextView = (TextView) findViewById(R.id.textview_year);
        imdbIdTextView = (TextView) findViewById(R.id.textview_imdbid);
        posterImageView = (ImageView) findViewById(R.id.imageview_poster);
        imageUriTextView = (TextView) findViewById(R.id.textview_imageUri);
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


        Results clickedMovie = getIntent().getParcelableExtra("MovieDetailActivity");

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + clickedMovie.getBackdrop())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        spinnerRelativeLayout.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        spinnerRelativeLayout.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(posterImageView);

        titleTextView.setText(clickedMovie.getTitle());

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + clickedMovie.getPoster())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        miniPosterSpinnerRL.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        miniPosterSpinnerRL.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(miniPosterImageView);
        yearTextView.setText("Year: " + clickedMovie.getYear());
        movieTypeTextView.setText("Overview: " + clickedMovie.getOverview());
        imdbIdTextView.setText("ID: " + clickedMovie.getId());
        imageUriTextView.setText("Vote: " + clickedMovie.getVote());

        getMovieDetail(clickedMovie.getId(), clickedMovie.getBackdrop());

    }

    public void getMovieDetail(String id, final String backDropUri) {

        RetrofitManager.getInstance().getMovieDetail(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> vidoes = new ArrayList(response.body().getResults());
                    myVideos = vidoes;
                    videoId = vidoes.get(0).getKey();
                    if (myYoutubePlayer == null) {
                        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, MovieDetailActivity.this);
                    } else {
                        myYoutubePlayer.cueVideo(videoId);
                    }

                    videoListAdapter.setValues(vidoes, backDropUri);


                } else {
                    Toast.makeText(MovieDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        }, id);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + videoListAdapter.getItem(position) + ", which is at cell position " + position);
        Log.i("clickedmovie vote", videoListAdapter.getItem(position).getKey());
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
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
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
}
