package com.example.anuj.imdbmovielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton imageButton;
    private EditText editText;
    private String searchQuery;
    private ArrayList<Search> myMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview_githubrepos);
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


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
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

                listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, movies));

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        });
    }

}
