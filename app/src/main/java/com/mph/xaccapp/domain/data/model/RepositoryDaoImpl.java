package com.mph.xaccapp.domain.data.model;

import android.support.annotation.NonNull;

import java.util.List;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * Created by Marcos on 17/12/2017.
 */

public class RepositoryDaoImpl implements RepositoryDao {

    @NonNull
    private EntityDataStore<Persistable> mDataStore;

    public RepositoryDaoImpl(@NonNull EntityDataStore<Persistable> mDataStore) {
        this.mDataStore = mDataStore;
    }

    @Override
    public List<Repository> getRepositories(int page, int elementsPerPage) {
        return mDataStore
                .select(Repository.class)
                .orderBy(Repository.NAME.lower())
                .limit(elementsPerPage)
                .offset(page * elementsPerPage)
                .get()
                .toList();
    }

    @Override
    public Repository getRepository(String id) {
        return mDataStore
                .select(Repository.class)
                .where(Repository.ID.eq(id))
                .get()
                .firstOrNull();
    }

    @Override
    public void deleteAllRepositories() {
        mDataStore.delete(Repository.class).get().value();
    }

    @Override
    public void deleteRepository(String id) {
        mDataStore
                .delete(Repository.class)
                .where(Repository.ID.eq(id))
                .get()
                .value();
    }

    @Override
    public void insertRepository(Repository repository) {
        mDataStore.insert(repository);
    }

    @Override
    public int getRepositoryCount() {
        return mDataStore.count(Repository.class).get().value();
    }
}
