package com.mph.xaccapp.domain.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.network.model.RestRepository;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import java.util.List;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public class RepoRepositoryImpl implements RepoRepository {

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
    public void getRepos(GetReposListener listener) {

    }

    @Override
    public void getRepos(int page, int maxCount, final GetReposListener listener) {
        if (shouldLoadFromRemoteStore(page, maxCount)) {
            mRepositoryService.getRepositories(page, maxCount,
                    new RepositoryService.OnFetchCompletedListener() {
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
        else {
            listener.onReposLoaded(getLocalEntities(page, maxCount));
        }
    }

    @Override
    public void getRepos(int maxCount, final GetReposListener listener) {

    }

    @Override
    public void clearRepos(final DeleteReposListener listener) {
        deleteAllEntities();
        listener.onDeleteSuccess();
    }

    private boolean shouldLoadFromRemoteStore(int page, int elementsPerPage) {
        return localRepoCount() < elementsPerPage * (page + 1);
    }

    private int localRepoCount() {
        return mDataStore.count(Repository.class).get().value();
    }

    private List<Repository> getLocalEntities(int page, int elementsPerPage) {
        return mDataStore
                .select(Repository.class)
                .orderBy(Repository.NAME.lower())
                .limit(elementsPerPage)
                .offset(page * elementsPerPage)
                .get()
                .toList();
    }

    private Repository getLocalEntity(String id) {
        return mDataStore
                .select(Repository.class)
                .where(Repository.ID.eq(id))
                .get()
                .firstOrNull();
    }

    private void saveFetchedEntities(Iterable<Repository> repositories) {
        rewriteEntities(repositories);
    }

    private void rewriteEntities(Iterable<Repository> repositories) {
        for (Repository repository : repositories) {
            rewriteEntity(repository);
        }
    }

    private void rewriteEntity(Repository repository) {
        deleteEntity(repository.getId());
        persistEntity(repository);
    }

    private void deleteAllEntities() {
        mDataStore.delete(Repository.class).get().value();
    }

    private void persistEntity(Repository repository) {
        mDataStore.insert(repository);
    }

    private void deleteEntity(String id) {
        mDataStore
                .delete(Repository.class)
                .where(Repository.ID.eq(id))
                .get()
                .value();
    }
}
