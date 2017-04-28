package com.example.anuj.imdbmovielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//    private GridView gridView;
    private ImageButton imageButton;
    private EditText editText;
    private String searchQuery;
    private ArrayList<Results> myMovies;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView) findViewById(R.id.listview_githubrepos);
//        gridView = (GridView) findViewById(R.id.gridView_movielist);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_movieList);
        imageButton = (ImageButton) findViewById(R.id.imagebutton_search);
        editText = (EditText) findViewById(R.id.edittext_search);

        myMovies = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        movieListAdapter = new MovieListAdapter(MainActivity.this, myMovies);
        recyclerView.setAdapter(movieListAdapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);

            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchQuery = editText.getText().toString();
                    CallRetrofit(searchQuery);
                    return true;
                }
                return false;
            }
        });

        searchQuery = "a";
        CallRetrofit(searchQuery);


    }

    public void CallRetrofit(String searchParam) {
//        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        RetrofitManager.getInstance().getPopularMovies(new Callback<TmdbResponse>() {
            @Override
            public void onResponse(Call<TmdbResponse> call, Response<TmdbResponse> response) {

                if (response.code() == 200) {
                    ArrayList<Results> movies = new ArrayList(response.body().getResults());
                    myMovies = movies;
                    movieListAdapter.setValues(movies);

                }else{
//                    Log.i(TAG, "onResponse: " + response);
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
