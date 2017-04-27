package com.example.anuj.imdbmovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


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
    private ArrayList<Search> myMovies;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView) findViewById(R.id.listview_githubrepos);
//        gridView = (GridView) findViewById(R.id.gridView_movielist);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_movieList);
        imageButton = (ImageButton) findViewById(R.id.imagebutton_search);
        editText = (EditText) findViewById(R.id.edittext_search);

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
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okclient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .client(okclient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // Creating an object of our api interface
        ImdbClient client = retrofit.create(ImdbClient.class);

        Call<MovieResponse> call = client.reposForUser(searchParam);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                ArrayList<Search> movies = new ArrayList(response.body().getSearch());
                myMovies = movies;

//                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, movies));
//                gridView.setAdapter(new GitHubRepoAdapter(MainActivity.this, movies));
                int numberOfColumns = 2;
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
                recyclerView.setAdapter(new GitHubRepoAdapter(MainActivity.this, movies));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        });
    }

}
