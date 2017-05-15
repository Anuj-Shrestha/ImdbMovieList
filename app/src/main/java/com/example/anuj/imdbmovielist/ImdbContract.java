package com.example.anuj.imdbmovielist;

import java.util.ArrayList;

/**
 * Created by anuj on 5/4/17.
 */

public interface ImdbContract {

    interface View {
        void showSearchBox();
        void removeSearchBox();
        void removeSpinner();
        void setMovieListAdapterData(ArrayList<Results> movies);
        void displayErrorMessage(String message);
    }

    interface Presenter {
        void searchMovies(String searchQuery);
        void setImdbiView(ImdbContract.View imdbView);
        void onShowSearchBox();
        void onRemoveSearchBox();
    }
}
