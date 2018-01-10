package com.mph.xaccapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

public class GetRepositoriesInteractorImpl implements GetRepositoriesInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = GetRepositoriesInteractorImpl.class.getSimpleName();

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
    public Observable<List<Repository>> execute(final boolean forceRefresh,
                                                final int elementsPerPage, final int page) {
        // TODO: 10/01/2018 Use a disposable observer to avoid memory leaks
        return  handleForceRefresh(forceRefresh)
                .andThen(fetchIfNeeded(forceRefresh, elementsPerPage, page))
                .andThen(mRepoRepository.getRepoPage(page, elementsPerPage))
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }

    private Completable handleForceRefresh(final boolean forceRefresh) {
        if (forceRefresh) {
            return mRepoRepository.clearRepos();
        }
        else {
            return Completable.complete();
        }
    }

    private Completable fetchIfNeeded(boolean forceRefresh,
                                      final int elementsPerPage,
                                      final int page) {
        if (shouldLoadFromRemoteStore(forceRefresh, page, elementsPerPage)) {
            return mRepoRepository.fetchRemoteRepos(page, elementsPerPage);
        }
        else {
            return Completable.complete();
        }
    }

    private boolean shouldLoadFromRemoteStore(boolean forceRefresh, int page, int elementsPerPage) {
        return forceRefresh || (mRepoRepository.localRepoCount() < elementsPerPage * (page + 1));
    }
}
