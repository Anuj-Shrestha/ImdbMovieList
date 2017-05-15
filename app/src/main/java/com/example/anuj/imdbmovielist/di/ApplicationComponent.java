package com.example.anuj.imdbmovielist.di;

import com.example.anuj.imdbmovielist.MainActivity;
import com.example.anuj.imdbmovielist.MovieDetail.MovieDetailActivity;

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
