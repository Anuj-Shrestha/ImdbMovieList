package com.example.anuj.imdbmovielist;

import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Created by anuj on 5/4/17.
 */

public interface ImdbInteractor {
    void searchMovies(String searchQuerry, SearchMovieCallback searchMovieCallback);

    interface SearchMovieCallback {
        void removeSpinner();
        void setMovieListAdapterData(ArrayList<Results> movies);
        void displayErrorMessage(String errorMessage);
        void onCancelled();
    }
}
