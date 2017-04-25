package com.example.anuj.imdbmovielist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

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


        Search clickedMovie = (Search) getIntent().getSerializableExtra("MovieDetail");
        String movieposter = clickedMovie.getUri();

        GetImage getImageCall = new GetImage(poster, spinner);
        getImageCall.execute(clickedMovie.getUri());

        title.setText(clickedMovie.getTitle());
        new GetImage(miniPoster, miniposterspinner).execute(clickedMovie.getUri());
        year.setText("Year: " + clickedMovie.getYear());
        type.setText("Type: " + clickedMovie.getType());
        imdbid.setText("Imdb ID: " + clickedMovie.getImdbID());
        imageUri.setText("Image URL: " + clickedMovie.getUri());

        Log.i("sent data", clickedMovie.getTitle());

    }
}
