package com.example.anuj.imdbmovielist.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anuj on 5/15/17.
 */

@Module
public class ApplicationModule {

    private Application application;

    ApplicationModule (Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }
}
