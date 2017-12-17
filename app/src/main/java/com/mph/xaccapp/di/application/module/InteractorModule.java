package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.main.GetRepositoriesInteractor;
import com.mph.xaccapp.main.GetRepositoriesInteractorImpl;
import com.mph.xaccapp.main.RepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class InteractorModule {

    @Provides
    @Singleton
    GetRepositoriesInteractor provideGetRepositoriesInteractor(RepoRepository repoRepository) {
        return new GetRepositoriesInteractorImpl(repoRepository);
    }

    public interface Exposes {
        GetRepositoriesInteractor getRepositoriesInteractor();
    }
}
