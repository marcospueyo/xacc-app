package com.mph.xaccapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetRepositoriesInteractorImpl implements GetRepositoriesInteractor {

    @NonNull
    private final RepoRepository mRepoRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
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
        return mRepoRepository
                .clearRepos(forceRefresh)
                .andThen(mRepoRepository.getRepos(page, elementsPerPage))
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
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
