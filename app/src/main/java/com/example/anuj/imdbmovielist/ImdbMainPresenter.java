package com.example.anuj.imdbmovielist;

import java.util.ArrayList;


/**
 * Created by anuj on 5/4/17.
 */

public class ImdbMainPresenter implements ImdbContract.Presenter {
    private ImdbContract.View imdbView;
    private ImdbInteractor imdbInteractor;
    private ImdbInteractor.SearchMovieCallback searchMovieCallback;
    public ImdbMainPresenter(ImdbInteractor imdbInteractor) {
        this.imdbInteractor = imdbInteractor;
        this.searchMovieCallback = new SearchCallbackImplementation();
    }



    public void setMainActivityView(ImdbContract.View ImdbView) {
        this.imdbView = ImdbView;
    }

    public void onShowSearchBox() {
        imdbView.showSearchBox();
    }

    public void onRemoveSearchBox() {
        imdbView.removeSearchBox();
    }

    @Override
    public void searchMovies(String searchQuery) {
        imdbInteractor.searchMovies(searchQuery, searchMovieCallback);
    }

    public class SearchCallbackImplementation implements ImdbInteractor.SearchMovieCallback {

        @Override
        public void removeSpinner() {
            imdbView.removeSpinner();

        }

        @Override
        public void setMovieListAdapterData(ArrayList<Results> movies) {
            imdbView.setMovieListAdapterData(movies);
        }

        @Override
        public void displayErrorMessage(String errorMessage) {
            imdbView.displayErrorMessage("Error has occured");
        }

        @Override
        public void onCancelled() {
        }
    }

}
