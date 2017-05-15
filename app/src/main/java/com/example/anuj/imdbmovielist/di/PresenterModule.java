package com.example.anuj.imdbmovielist.di;

import com.example.anuj.imdbmovielist.ImdbContract;
import com.example.anuj.imdbmovielist.ImdbInteractor;
import com.example.anuj.imdbmovielist.ImdbMainPresenter;
import com.example.anuj.imdbmovielist.ImdbSearchManager;
import com.example.anuj.imdbmovielist.MovieDetail.FetchVideoManager;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailContract;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailInteractor;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailPresenter;
import com.example.anuj.imdbmovielist.RetrofitManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anuj on 5/15/17.
 */

@Module
public class PresenterModule {

    @Provides
    ImdbContract.Presenter provideImdbMainPresenter(ImdbInteractor imdbInteractor) {
        return new ImdbMainPresenter(imdbInteractor);
    }

    @Provides
    ImdbInteractor provideImdbInteractor(RetrofitManager retrofitManager) {
        return new ImdbSearchManager(retrofitManager);
    }

    @Provides
    MovieDetailContract.Presenter provideMovieDetailPresenter(MovieDetailInteractor movieDetailInteractor) {
        return new MovieDetailPresenter(movieDetailInteractor);
    }

    @Provides
    MovieDetailInteractor provideMovieDetailInteractor(RetrofitManager retrofitManager) {
        return new FetchVideoManager(retrofitManager);
    }
}
