package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.network.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcos on 17/12/2017.
 */

@Module
public final class NetworkModule {

    public static final String API_URL = "https://api.github.com/";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    public interface Exposes {
        GithubService githubService();
    }
}
