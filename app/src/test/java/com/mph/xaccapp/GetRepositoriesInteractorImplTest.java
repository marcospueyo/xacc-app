package com.mph.xaccapp;
/* Created by macmini on 08/01/2018. */

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractorImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public final class GetRepositoriesInteractorImplTest {

    private RepoRepository mRepoRepository;

    private Scheduler mMainScheduler;

    private Scheduler mBackgroundScheduler;

    private GetRepositoriesInteractorImpl mGetRepositoriesInteractor;

    @Before
    public void setUp() throws Exception {
        mRepoRepository = mock(RepoRepository.class);
        mMainScheduler = Schedulers.trampoline();
        mBackgroundScheduler = Schedulers.trampoline();
        mGetRepositoriesInteractor = new GetRepositoriesInteractorImpl(mRepoRepository,
                mMainScheduler, mBackgroundScheduler);

        when(mRepoRepository.getRepoPage(anyInt(), anyInt()))
                .thenReturn(new Observable<List<Repository>>() {
                    @Override
                    protected void subscribeActual(Observer<? super List<Repository>> observer) {
                        observer.onNext(new ArrayList<Repository>());
                        observer.onComplete();
                    }
                });

        when(mRepoRepository.clearRepos()).thenReturn(Completable.complete());
        when(mRepoRepository.fetchRemoteRepos(anyInt(), anyInt())).thenReturn(Completable.complete());

    }

    @Test
    public void shouldDeleteRepos() throws Exception {
        when(mRepoRepository.localRepoCount()).thenReturn(0);

        mGetRepositoriesInteractor.execute(true, 10, 0);
        verify(mRepoRepository).clearRepos();
    }

    @Test
    public void shouldLoadFromRepositoryOnRefresh() throws Exception {
        when(mRepoRepository.localRepoCount()).thenReturn(0);

        int elementsPerPage = 10;
        int page = 0;

        mGetRepositoriesInteractor.execute(true, elementsPerPage, page);
        verify(mRepoRepository, times(1))
                .fetchRemoteRepos(eq(page), eq(elementsPerPage));
    }

    @Test
    public void shouldLoadLocalRepositories() throws Exception {
        when(mRepoRepository.localRepoCount()).thenReturn(500);

        int elementsPerPage = 10;
        int page = 2;

        mGetRepositoriesInteractor.execute(false, elementsPerPage, page);
        verify(mRepoRepository, never()).fetchRemoteRepos(anyInt(), anyInt());
        verify(mRepoRepository, times(1))
                .getRepoPage(eq(page), eq(elementsPerPage));
    }
}
