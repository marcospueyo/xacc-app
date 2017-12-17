package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.main.RepositoryViewModelMapper;

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

    public interface Exposes {
        RepositoryViewModelMapper repositoryViewModelMapper();
    }
}
