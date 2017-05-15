package com.example.anuj.imdbmovielist.di;

import android.app.Application;
import android.content.Context;

import com.example.anuj.imdbmovielist.ImdbSearchManager;
import com.example.anuj.imdbmovielist.MainActivity;
import com.example.anuj.imdbmovielist.MovieDetail.FetchVideoManager;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailActivity;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailContract;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anuj on 5/15/17.
 */

@Singleton
@Component (modules = {ApplicationModule.class, NetworkModule.class, PresenterModule.class})
public interface ApplicationComponent {
    void inject(MovieDetailActivity movieDetailActivity);

    void inject(MainActivity mainActivity);
}
