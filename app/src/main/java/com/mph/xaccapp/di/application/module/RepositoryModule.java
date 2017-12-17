package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.main.RepoRepository;
import com.mph.xaccapp.main.RepoRepositoryImpl;
import com.mph.xaccapp.main.RepositoryService;
import com.mph.xaccapp.network.RestRepositoryMapper;

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
