package com.mph.xaccapp.domain.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.network.model.RestRepository;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public class RepoRepositoryImpl implements RepoRepository {

    @NonNull
    private final RepositoryService mRepositoryService;

    @NonNull
    private RepositoryDao mRepositoryDao;

    @NonNull
    private final RestRepositoryMapper mMapper;

    @NonNull
    private final Scheduler mBackgroundThread;

    public RepoRepositoryImpl(@NonNull RepositoryService repositoryService,
                              @NonNull RepositoryDao repositoryDao,
                              @NonNull RestRepositoryMapper mapper,
                              @NonNull Scheduler backgroundThread) {
        mRepositoryService = repositoryService;
        mRepositoryDao = repositoryDao;
        mMapper = mapper;
        mBackgroundThread = backgroundThread;
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
    public Observable<List<Repository>> getRepos(final int page, final int maxCount) {
        return Observable.create(new ObservableOnSubscribe<List<Repository>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Repository>> emitter) throws Exception {
                if (shouldLoadFromRemoteStore(page, maxCount)) {
                    mRepositoryService.getRepositories(page, maxCount,
                            new RepositoryService.OnFetchCompletedListener() {
                                @Override
                                public void onRepositoriesFetched(List<RestRepository> restRepositories) {
                                    List<Repository> repositories = mMapper.map(restRepositories);
                                    emitter.onNext(repositories);
                                    emitter.onComplete();
                                    saveFetchedEntities(repositories);
                                }

                                @Override
                                public void onFetchFailed() {
                                    emitter.onError(new Throwable("Error fetching repositories " +
                                            "from repository service"));
                                }
                            });
                }
                else {
                    emitter.onNext(getLocalEntities(page, maxCount));
                    emitter.onComplete();
                }
            }
        });
    }

    @Override
    public void clearRepos(final DeleteReposListener listener) {
        deleteAllEntities();
        listener.onDeleteSuccess();
    }

    @Override
    public Completable clearRepos(final boolean forceRefresh) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                if (forceRefresh)
                    deleteAllEntities();
            }
        }).subscribeOn(mBackgroundThread);
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
