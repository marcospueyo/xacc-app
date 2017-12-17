package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.presentation.mapper.RepositoryViewModelMapper;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class MappersModule {

    @Provides
    @Singleton
    RepositoryViewModelMapper provideRepositoryViewModelMapper() {
        return new RepositoryViewModelMapper();
    }

    @Provides
    @Singleton
    RestRepositoryMapper provideRestRepositoryMapper() {
        return new RestRepositoryMapper();
    }

    public interface Exposes {
        RepositoryViewModelMapper repositoryViewModelMapper();
        RestRepositoryMapper restRepositoryMapper();
    }
}
