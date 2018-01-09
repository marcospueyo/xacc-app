package com.mph.xaccapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;

public class GetRepositoriesInteractorImpl implements GetRepositoriesInteractor {

    @NonNull
    private final RepoRepository mRepoRepository;

    private final Scheduler mBackgroundThread;
    private final Scheduler mMainThread;

    public GetRepositoriesInteractorImpl(@NonNull RepoRepository repoRepository,
                                         @NonNull Scheduler mainThread,
                                         @NonNull Scheduler backgroundThread) {
        mRepoRepository = repoRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public void execute(boolean forceRefresh, final int elementsPerPage, final int page,
                        final OnFinishedListener listener) {
        if (forceRefresh) {
            mRepoRepository.clearRepos(new RepoRepository.DeleteReposListener() {
                @Override
                public void onDeleteSuccess() {
                    getRepos(elementsPerPage, page, listener);
                }

                @Override
                public void onDeleteError() {

                }
            });
        }
        else {
            getRepos(elementsPerPage, page, listener);
        }
    }

    @Override
    public Observable<List<Repository>> execute(final boolean forceRefresh,
                                                final int elementsPerPage, final int page) {
        return Observable
                .create(new ObservableOnSubscribe<List<Repository>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Repository>> emitter) throws Exception {
                if (forceRefresh) {
                    mRepoRepository.clearRepos(new RepoRepository.DeleteReposListener() {
                        @Override
                        public void onDeleteSuccess() {
                            getRepos(page, elementsPerPage, emitter);
                        }

                        @Override
                        public void onDeleteError() {

                        }
                    });
                }
                else {
                    getRepos(page, elementsPerPage, emitter);
                }
            }
        })
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }

    private void getRepos(int page, int elementsPerPage,
                          final ObservableEmitter<List<Repository>> emitter) {
        mRepoRepository.getRepos(page, elementsPerPage,
                new RepoRepository.GetReposListener() {
                    @Override
                    public void onReposLoaded(List<Repository> repositories) {
                        emitter.onNext(repositories);
                        emitter.onComplete();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        emitter.onError(null);
                    }
                });
    }

    private void getRepos(int elementsPerPage, int page, final OnFinishedListener listener) {
        mRepoRepository.getRepos(page, elementsPerPage,
                new RepoRepository.GetReposListener() {
                    @Override
                    public void onReposLoaded(List<Repository> repositories) {
                        listener.onRepositoriesLoaded(repositories);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        listener.onLoadError();
                    }
                });
    }
}
