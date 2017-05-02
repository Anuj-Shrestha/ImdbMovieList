package com.example.anuj.imdbmovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, LifecycleExampleActivity.class);
                startActivity(intent);
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
                    return true;
                }
                return false;
            }
        });

        searchQuery = "a";
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
        });

    }

}
