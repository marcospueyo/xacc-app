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
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
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

    private Function<List<Repository>, List<Repository>> saveFetchedEntities() {
        return new Function<List<Repository>, List<Repository>>() {
            @Override
            public List<Repository> apply(List<Repository> repositories) throws Exception {
                saveFetchedEntities(repositories);
                return repositories;
            }
        };
    }

    private Observable<List<Repository>> fetchFromRemoteStore(final int page, final int maxCount) {
        return mRepositoryService
                .getRepositories(page, maxCount)
                .map(mMapper.map());
    }

    @Override
    public Observable<List<Repository>> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<Repository>> getRepoPage(int page, int maxCount) {
        return getLocalEntitiesObservable(page, maxCount);
    }

    @Override
    public Completable fetchRemoteRepos(final int page, final int maxCount) {
        return Completable.fromObservable(
                fetchFromRemoteStore(page, maxCount)
                        .map(saveFetchedEntities()));
    }

    @Override
    public Completable clearRepos() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                    deleteAllEntities();
            }
        });
    }

    @Override
    public int localRepoCount() {
        return mRepositoryDao.getRepositoryCount();
    }

    private Observable<List<Repository>> getLocalEntitiesObservable(final int page,
                                                                    final int elementsPerPage) {
        return mRepositoryDao.getRepositories(page, elementsPerPage);
    }

    private void saveFetchedEntities(Iterable<Repository> repositories) {
        mRepositoryDao.insertRepositories(repositories);
    }

    private void deleteAllEntities() {
        mRepositoryDao.deleteAllRepositories();
    }
}
