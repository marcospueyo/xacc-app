package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.network.service.RepositoryServiceImpl;
import com.mph.xaccapp.network.service.api.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public final class ServiceModule {

    public static final String USER_NAME = "xing";

    @Provides
    @Singleton
    RepositoryService provideRepositoryService(GithubService githubService) {
        return new RepositoryServiceImpl(githubService, USER_NAME);
    }

    public interface Exposes {
        RepositoryService repositoryService();
    }
}
