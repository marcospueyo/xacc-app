package com.mph.xaccapp.domain.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.domain.data.model.RepositoryDao;
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
    private RepositoryDao mRepositoryDao;

    @NonNull
    private final RestRepositoryMapper mMapper;

    public RepoRepositoryImpl(@NonNull RepositoryService repositoryService,
                              @NonNull RepositoryDao repositoryDao,
                              @NonNull RestRepositoryMapper mapper) {
        mRepositoryService = repositoryService;
        mRepositoryDao = repositoryDao;
        mMapper = mapper;
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
    public void clearRepos(final DeleteReposListener listener) {
        deleteAllEntities();
        listener.onDeleteSuccess();
    }

    private boolean shouldLoadFromRemoteStore(int page, int elementsPerPage) {
        return localRepoCount() < elementsPerPage * (page + 1);
    }

    private int localRepoCount() {
        return mRepositoryDao.getRepositoryCount();
    }

    private List<Repository> getLocalEntities(int page, int elementsPerPage) {
        return mRepositoryDao.getRepositories(page, elementsPerPage);
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
        mRepositoryDao.deleteAllRepositories();
    }

    private void persistEntity(Repository repository) {
        mRepositoryDao.insertRepository(repository);
    }

    private void deleteEntity(String id) {
        mRepositoryDao.deleteRepository(id);
    }
}
