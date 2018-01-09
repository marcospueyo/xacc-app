package com.mph.xaccapp;
/* Created by macmini on 08/01/2018. */

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractorImpl;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Scheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public final class GetRepositoriesInteractorImplTest {

    private RepoRepository mRepoRepository;

    private Scheduler mMainScheduler;

    private Scheduler mBackgroundScheduler;

    private GetRepositoriesInteractorImpl mGetRepositoriesInteractor;

    private GetRepositoriesInteractor.OnFinishedListener listener;

    @Before
    public void setUp() throws Exception {
        mRepoRepository = mock(RepoRepository.class);
        mMainScheduler = mock(Scheduler.class);
        mBackgroundScheduler = mock(Scheduler.class);
        mGetRepositoriesInteractor = new GetRepositoriesInteractorImpl(mRepoRepository,
                mMainScheduler, mBackgroundScheduler);
        listener = mock(GetRepositoriesInteractor.OnFinishedListener.class);
    }

    @Test
    public void shouldDeleteRepos() throws Exception {
        mGetRepositoriesInteractor.execute(true, 10, 0, listener);
        verify(mRepoRepository, times(1))
                .clearRepos(any(RepoRepository.DeleteReposListener.class));
    }

    @Test
    public void shouldLoadFromRepositoryOnRefresh() throws Exception {
// TODO: 08/01/2018 Must be able to mock RepoRepository delete callback
    }

    @Test
    public void shouldLoadFromRepositoryInStandardExecution() throws Exception {
        int elementsPerPage = 10;
        int page = 0;
        mGetRepositoriesInteractor.execute(false, elementsPerPage, page, listener);
        verify(mRepoRepository, times(1))
                .getRepos(eq(page), eq(elementsPerPage), any(RepoRepository.GetReposListener.class));
    }
}
