package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractorImpl;
import com.mph.xaccapp.domain.data.RepoRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public final class InteractorModule {

    @Provides
    @Singleton
    GetRepositoriesInteractor provideGetRepositoriesInteractor(RepoRepository repoRepository,
       @Named(ThreadingModule.MAIN_SCHEDULER) Scheduler mainThread,
       @Named(ThreadingModule.BACKGROUND_SCHEDULER) Scheduler backgroundThread) {
        return new GetRepositoriesInteractorImpl(repoRepository, mainThread, backgroundThread);
    }

    public interface Exposes {
        GetRepositoriesInteractor getRepositoriesInteractor();
    }
}
