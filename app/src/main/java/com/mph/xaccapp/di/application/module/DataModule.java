package com.mph.xaccapp.di.application.module;

import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.domain.data.model.RepositoryDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * Created by Marcos on 17/12/2017.
 */

@Module
public final class DataModule {

    @Provides
    @Singleton
    RepositoryDao provideRepositoryDao(EntityDataStore<Persistable> dataStore) {
        return new RepositoryDaoImpl(dataStore);
    }

    public interface Exposes {

        RepositoryDao repositoryDao();

    }
}
