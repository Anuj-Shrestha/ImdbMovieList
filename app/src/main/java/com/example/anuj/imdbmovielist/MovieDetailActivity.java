package com.example.anuj.imdbmovielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    TextView titleTextView, movieTypeTextView, yearTextView, imdbIdTextView, imageUriTextView;
    ImageView posterImageView, miniPosterImageView;
    RelativeLayout spinnerRelativeLayout, miniPosterSpinnerRL;
    RecyclerView recyclerView;
    ArrayList <Results> myVideos;
    VideoListAdapter videoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = (TextView) findViewById(R.id.textview_title);
        movieTypeTextView = (TextView) findViewById(R.id.textview_type);
        yearTextView = (TextView) findViewById(R.id.textview_year);
        imdbIdTextView = (TextView) findViewById(R.id.textview_imdbid);
        posterImageView = (ImageView) findViewById(R.id.imageview_poster);
        imageUriTextView = (TextView)findViewById(R.id.textview_imageUri);
        miniPosterImageView = (ImageView) findViewById(R.id.imageview_miniPoster);
        spinnerRelativeLayout = (RelativeLayout) findViewById(R.id.loadingPanel_poster);
        miniPosterSpinnerRL = (RelativeLayout) findViewById(R.id.loadingPanel_miniposter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_videoList);
        myVideos = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(MovieDetailActivity.this, 2));
        videoListAdapter = new VideoListAdapter(MovieDetailActivity.this, myVideos);
        recyclerView.setAdapter(videoListAdapter);


        Results clickedMovie = getIntent().getParcelableExtra("MovieDetailActivity");
        Log.i("clickedmovie image", clickedMovie.getBackdrop());
        Log.i("clickedmovie overview", clickedMovie.getOverview());
        Log.i("clickedmovie vote", clickedMovie.getVote());

//        GetImageAsyncTask getImageCall = new GetImageAsyncTask(posterImageView, spinnerRelativeLayout);
//        getImageCall.execute(clickedMovie.getUri());
//        Glide.with(this).load(clickedMovie.getUri()).into(posterImageView);
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
//        new GetImageAsyncTask(miniPosterImageView, miniPosterSpinnerRL).execute(clickedMovie.getUri());
//        Glide.with(this).load(clickedMovie.getUri()).into(miniPosterImageView);
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

        getMovieDetail(clickedMovie.getId());

    }
    public void getMovieDetail(String id) {

        RetrofitManager.getInstance().getMovieDetail(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> vidoes = new ArrayList(response.body().getResults());
                    myVideos = vidoes;
                    videoListAdapter.setValues(vidoes);

                }else{
//                    Log.i(TAG, "onResponse: " + response);
                    Toast.makeText(MovieDetailActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        }, id);



    }
}
