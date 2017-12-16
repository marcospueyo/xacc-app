package com.mph.xaccapp.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.model.Repository;
import com.mph.xaccapp.network.RestRepository;
import com.mph.xaccapp.network.RestRepositoryMapper;

import java.util.List;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public class RepoRepositoryImpl implements RepoRepository {

    public static final String TAG = "RepoRepositoryImpl";

    @NonNull
    private final RepositoryService mRepositoryService;

    @NonNull
    private EntityDataStore<Persistable> mDataStore;

    @NonNull
    private final RestRepositoryMapper mMapper;

    public RepoRepositoryImpl(@NonNull RepositoryService repositoryService,
                              @NonNull EntityDataStore<Persistable> dataStore,
                              @NonNull RestRepositoryMapper mapper) {
        mRepositoryService = repositoryService;
        mDataStore = dataStore;
        mMapper = mapper;
    }

    @Override
    public void getRepos(final GetReposListener listener) {
        if (shouldLoadFromRemoteStore()) {
            mRepositoryService.getRepositories(new RepositoryService.OnFetchCompletedListener() {
                @Override
                public void onRepositoriesFetched(List<RestRepository> restRepositories) {
                    List<Repository> repositories = mMapper.map(restRepositories);
                    saveFetchedEntities(repositories);
                    listener.onReposLoaded(repositories);
                }

                @Override
                public void onFetchFailed() {
                    listener.onDataNotAvailable();
                }
            });
        }
    }

    private boolean shouldLoadFromRemoteStore() {
        return true;
    }

    private void saveFetchedEntities(Iterable<Repository> repositories) {
        deleteEntities();
        persistEntities(repositories);
    }

    private void deleteEntities() {
        mDataStore.delete(Repository.class).get().value();
    }

    private void persistEntities(Iterable<Repository> repositories) {
        mDataStore.insert(repositories);
    }
}
