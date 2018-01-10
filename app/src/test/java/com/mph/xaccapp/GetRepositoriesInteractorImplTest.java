package com.mph.xaccapp;
/* Created by macmini on 08/01/2018. */

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractorImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class GetRepositoriesInteractorImplTest {

    private RepoRepository mRepoRepository;

    private Scheduler mMainScheduler;

    private Scheduler mBackgroundScheduler;

    private GetRepositoriesInteractorImpl mGetRepositoriesInteractor;

//    private TestSubscriber<List<Repository>> testSubscriber;

    @Before
    public void setUp() throws Exception {
        mRepoRepository = mock(RepoRepository.class);
        mMainScheduler = /*new TestScheduler()*/ mock(Scheduler.class);
        mBackgroundScheduler = /*new TestScheduler()*/ mock(Scheduler.class);
        mGetRepositoriesInteractor = new GetRepositoriesInteractorImpl(mRepoRepository,
                mMainScheduler, mBackgroundScheduler);
//        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldDeleteRepos() throws Exception {
        when(mRepoRepository.getLocalRepos(anyBoolean(), anyInt(), anyInt()))
                .thenReturn(new Observable<List<Repository>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Repository>> observer) {

            }
        });
        mGetRepositoriesInteractor.execute(true, 10, 0);
        verify(mRepoRepository).clearRepos(true);
//        verify(mRepoRepository, times(1))
//                .clearRepos(any(RepoRepository.DeleteReposListener.class));
    }

    // TODO: 08/01/2018 Must be able to mock RepoRepository delete callback
//    @Test
//    public void shouldLoadFromRepositoryOnRefresh() throws Exception {
//    }

    @Test
    public void shouldLoadFromRepositoryInStandardExecution() throws Exception {
        int elementsPerPage = 10;
        int page = 0;
        mGetRepositoriesInteractor.execute(false, elementsPerPage, page);
        verify(mRepoRepository, times(1))
                .getLocalRepos(eq(false), eq(page), eq(elementsPerPage));
    }
}
