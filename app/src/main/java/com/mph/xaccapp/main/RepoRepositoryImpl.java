package com.mph.xaccapp.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.network.RestRepository;

import java.util.List;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public class RepoRepositoryImpl implements RepoRepository {

    public static final String TAG = "RepoRepositoryImpl";

    @NonNull
    private final RepositoryService mRepositoryService;

    @NonNull
    private EntityDataStore<Persistable> mDataStore;

    public RepoRepositoryImpl(@NonNull RepositoryService repositoryService,
                              @NonNull EntityDataStore<Persistable> dataStore) {
        mRepositoryService = repositoryService;
        mDataStore = dataStore;
    }

    @Override
    public void getRepos(final GetReposListener listener) {
        if (shouldLoadFromRemoteStore()) {
            mRepositoryService.getRepositories(new RepositoryService.OnFetchCompletedListener() {
                @Override
                public void onRepositoriesFetched(List<RestRepository> restRepositories) {
                    Log.d(TAG, "onRepositoriesFetched: no. of repos = " + restRepositories.size());
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
}
