package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public final class RepositoryModule {

    @Provides
    @Singleton
    RepoRepository provideRepoRepository(RepositoryService repositoryService,
                                         RepositoryDao repositoryDao,
                                         RestRepositoryMapper mapper) {
        return new RepoRepositoryImpl(repositoryService, repositoryDao, mapper);
    }

    public interface Exposes {

        RepoRepository repoRepository();

    }
}
