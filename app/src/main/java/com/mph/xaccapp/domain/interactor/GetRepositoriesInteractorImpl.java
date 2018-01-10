package com.mph.xaccapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

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
        return mRepoRepository
                .getRepos(forceRefresh, page, elementsPerPage)
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }
}
