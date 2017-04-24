package com.example.anuj.imdbmovielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Search clickedMovie = (Search) getIntent().getSerializableExtra("MovieDetail");
        Log.i("sent data", clickedMovie.getTitle());

    }
}
