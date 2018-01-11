package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

import static com.mph.xaccapp.di.application.module.ThreadingModule.BACKGROUND_SCHEDULER;


@Module
public final class RepositoryModule {

    @Provides
    @Singleton
    RepoRepository provideRepoRepository(RepositoryService repositoryService,
                                         RepositoryDao repositoryDao,
                                         RestRepositoryMapper mapper,
                                         @Named(BACKGROUND_SCHEDULER) Scheduler scheduler) {
        return new RepoRepositoryImpl(repositoryService, repositoryDao, mapper, scheduler);
    }

    public interface Exposes {

        RepoRepository repoRepository();

    }
}
