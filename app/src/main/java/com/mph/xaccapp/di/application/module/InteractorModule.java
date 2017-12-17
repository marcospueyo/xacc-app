package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractorImpl;
import com.mph.xaccapp.domain.data.RepoRepository;

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
