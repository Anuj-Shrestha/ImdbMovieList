package com.example.anuj.imdbmovielist;

import android.app.Application;

import com.example.anuj.imdbmovielist.di.ApplicationComponent;
import com.example.anuj.imdbmovielist.di.DaggerApplicationComponent;

/**
 * Created by anuj on 5/15/17.
 */

public class ImdbMovieListApplication extends Application {

    ApplicationComponent applicationComponent;


    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder().build();
        }
        return applicationComponent;
    }

}
