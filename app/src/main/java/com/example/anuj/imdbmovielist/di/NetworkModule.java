package com.example.anuj.imdbmovielist.di;

import com.example.anuj.imdbmovielist.RetrofitManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by anuj on 5/15/17.
 */

@Module
public class NetworkModule {

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    @Provides
    RetrofitManager provideRetrofitManager(OkHttpClient okHttpClient) {
        return RetrofitManager.getInstance(okHttpClient);
    }
}
