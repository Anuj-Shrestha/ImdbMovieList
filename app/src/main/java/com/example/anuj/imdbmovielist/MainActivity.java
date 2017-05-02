package com.example.anuj.imdbmovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText editText;
    private String searchQuery;
    private ArrayList<Results> myMovies;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private RelativeLayout spinnerRelativeLayout;
    private Button popularButton, upcomingButton;
    private LinearLayout searchLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_movieList);
        imageButton = (ImageButton) findViewById(R.id.imagebutton_search);
        editText = (EditText) findViewById(R.id.edittext_search);
        spinnerRelativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_loadingPanel);
        myMovies = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        movieListAdapter = new MovieListAdapter(MainActivity.this, myMovies);
        recyclerView.setAdapter(movieListAdapter);

        popularButton = (Button) findViewById(R.id.button_popular);
        upcomingButton = (Button) findViewById(R.id.button_upcoming);
        searchLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchQuery = editText.getText().toString();
                    searchPopularMovies(searchQuery);
                    searchLinearLayout.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPopularMovies("popular");
                searchLinearLayout.setVisibility(View.GONE);

            }
        });

        upcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPopularMovies("upcoming");
                searchLinearLayout.setVisibility(View.GONE);

            }
        });

        searchQuery = "popular";
        searchPopularMovies(searchQuery);


    }

    public void searchPopularMovies(String searchParam) {

        RetrofitManager.getInstance().getPopularMovies(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {
                spinnerRelativeLayout.setVisibility(View.GONE);

                if (response.code() == 200) {
                    ArrayList<Results> movies = new ArrayList(response.body().getResults());
                    myMovies = movies;
                    movieListAdapter.setValues(movies);

                }else{
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TmdbResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        }, searchParam);

    }

}
