package com.example.anuj.imdbmovielist;

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

public class MainActivity extends AppCompatActivity implements ImdbContract.View {

    private ImageButton imageButton;
    private EditText editText;
    private String searchQuery;
    private ArrayList<Results> myMovies;
    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private RelativeLayout spinnerRelativeLayout;
    private Button popularButton, upcomingButton;
    private LinearLayout searchLinearLayout;

    private ImdbContract.Presenter imdbMainPresenter;

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

        imdbMainPresenter = new ImdbMainPresenter(new ImdbSearchManager());
        imdbMainPresenter.setImdbiView(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imdbMainPresenter.onShowSearchBox();
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    searchQuery = editText.getText().toString();
                    imdbMainPresenter.searchMovies(searchQuery);
                    imdbMainPresenter.onRemoveSearchBox();

                    return true;
                }
                return false;
            }
        });

        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imdbMainPresenter.searchMovies("popular");
                imdbMainPresenter.onRemoveSearchBox();

            }
        });

        upcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imdbMainPresenter.searchMovies("upcoming");
                imdbMainPresenter.onRemoveSearchBox();

            }
        });

        searchQuery = "popular";
        searchMovies(searchQuery);


    }

    public void showSearchBox() {
        searchLinearLayout.setVisibility(View.VISIBLE);
    }

    public void removeSearchBox() {
        searchLinearLayout.setVisibility(View.GONE);
    }

    public void removeSpinner() {
        spinnerRelativeLayout.setVisibility(View.GONE);
    }

    public void setMovieListAdapterData(ArrayList<Results> movies) {
        movieListAdapter.setValues(movies);
    }

    public void displayErrorMessage(String errorMessage) {
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void searchMovies(String searchParam) {
        imdbMainPresenter.searchMovies(searchParam);
    }

}
