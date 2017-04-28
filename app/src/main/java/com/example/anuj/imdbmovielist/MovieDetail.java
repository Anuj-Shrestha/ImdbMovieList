package com.example.anuj.imdbmovielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetail extends AppCompatActivity {
    TextView title, type, year, imdbid, imageUri;
    ImageView poster, miniPoster;
    RelativeLayout spinner, miniposterspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = (TextView) findViewById(R.id.textview_title);
        type = (TextView) findViewById(R.id.textview_type);
        year = (TextView) findViewById(R.id.textview_year);
        imdbid = (TextView) findViewById(R.id.textview_imdbid);
        poster = (ImageView) findViewById(R.id.imageview_poster);
        imageUri = (TextView)findViewById(R.id.textview_imageUri);
        miniPoster = (ImageView) findViewById(R.id.imageview_miniPoster);
        spinner = (RelativeLayout) findViewById(R.id.loadingPanel_poster);
        miniposterspinner = (RelativeLayout) findViewById(R.id.loadingPanel_miniposter);


        Results clickedMovie = getIntent().getParcelableExtra("MovieDetail");
        Log.i("clickedmovie title", clickedMovie.getTitle());
        Log.i("clickedmovie image", clickedMovie.getBackdrop());
        Log.i("clickedmovie overview", clickedMovie.getOverview());
        Log.i("clickedmovie vote", clickedMovie.getVote());
        Log.i("clickedmovie year", clickedMovie.getYear());

//        GetImage getImageCall = new GetImage(poster, spinner);
//        getImageCall.execute(clickedMovie.getUri());
//        Glide.with(this).load(clickedMovie.getUri()).into(poster);
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + clickedMovie.getBackdrop())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        spinner.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        spinner.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(poster);


        title.setText(clickedMovie.getTitle());
//        new GetImage(miniPoster, miniposterspinner).execute(clickedMovie.getUri());
//        Glide.with(this).load(clickedMovie.getUri()).into(miniPoster);
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + clickedMovie.getPoster())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        miniposterspinner.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        miniposterspinner.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(miniPoster);
        year.setText("Year: " + clickedMovie.getYear());
        type.setText("Overview: " + clickedMovie.getOverview());
        imdbid.setText("ID: " + clickedMovie.getId());
        imageUri.setText("Vote: " + clickedMovie.getVote());

        CallRetrofit(clickedMovie.getId());

    }
    public void CallRetrofit(String id) {

        RetrofitManager.getInstance().getMovieDetail(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> videos = new ArrayList(response.body().getResults());
                    Log.d("test", videos.get(0).getName());

                }else{
//                    Log.i(TAG, "onResponse: " + response);
                    Toast.makeText(MovieDetail.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                Toast.makeText(MovieDetail.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        }, id);



    }
}
