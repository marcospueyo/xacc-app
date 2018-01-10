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

    @Override
    public void getRepos(int page, int maxCount, final GetReposListener listener) {
        if (shouldLoadFromRemoteStore(false, page, maxCount)) {
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

    private Observable<List<Repository>> fetchReposFromProperStore(final boolean mustFetchFromRemote,
                                                                   final int page,
                                                                  final int maxCount) {
        return shouldLoadFromRemoteStore(mustFetchFromRemote, page, maxCount)
                ? fetchFromRemoteStore(page, maxCount) : getLocalEntitiesObservable(page, maxCount);
    }

    @Override
    public Observable<List<Repository>> getRepos(final boolean mustFetchFromRemote, final int page,
                                                 final int maxCount) {
        return clearRepos(mustFetchFromRemote)
                .andThen(fetchReposFromProperStore(mustFetchFromRemote, page, maxCount))
                .map(saveFetchedEntities());
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
        });
    }

    private boolean shouldLoadFromRemoteStore(boolean mustFetchFromRemote, int page,
                                              int elementsPerPage) {
        return mustFetchFromRemote || ( localRepoCount() < elementsPerPage * (page + 1) );
    }

    private int localRepoCount() {
        return mRepositoryDao.getRepositoryCount();
    }

    private List<Repository> getLocalEntities(int page, int elementsPerPage) {
        return mRepositoryDao.getRepositories(page, elementsPerPage);
    }

    private Observable<List<Repository>> getLocalEntitiesObservable(final int page,
                                                                    final int elementsPerPage) {
        return Observable.create(new ObservableOnSubscribe<List<Repository>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Repository>> emitter) throws Exception {
                emitter.onNext(mRepositoryDao.getRepositories(page, elementsPerPage));
                emitter.onComplete();
            }
        });
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
