package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


@Module
public final class RepositoryModule {

    @Provides
    @Singleton
    RepoRepository provideRepoRepository(RepositoryService repositoryService,
                                         EntityDataStore<Persistable> dataStore,
                                         RestRepositoryMapper mapper) {
        return new RepoRepositoryImpl(repositoryService, dataStore, mapper);
    }

    public interface Exposes {

        RepoRepository repoRepository();

    }
}
