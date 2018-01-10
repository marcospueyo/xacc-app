package com.mph.xaccapp;

import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.service.RepositoryService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


public final class RepoRepositoryImplTest {

    private RepositoryDao mRepositoryDao;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl mRepoRepositoryImpl;

    private RepositoryService mRepositoryService;

    private Scheduler mBackgroundScheduler;

    private int page;

    private int elementsPerPage;


    @Before
    public void setUp() throws Exception {
        mRepositoryDao = mock(RepositoryDao.class);
        mRepositoryService = mock(RepositoryService.class);
        mMapper = new RestRepositoryMapper();
        mBackgroundScheduler = new TestScheduler();
        page = 1;
        elementsPerPage = 10;

        mRepoRepositoryImpl = new RepoRepositoryImpl(mRepositoryService, mRepositoryDao, mMapper,
                mBackgroundScheduler);
    }

    @Test
    public void shouldLoadLocalRepos() throws Exception {
        when(mRepositoryDao.getRepositoryCount()).thenReturn(21);

        mRepoRepositoryImpl.getLocalRepos(false, 1, 10);

        verify(mRepositoryDao, times(1)).getRepositoryCount();
        verify(mRepositoryDao, times(1))
                .getRepositories(page, elementsPerPage);
        verifyNoMoreInteractions(mRepositoryDao);
        verifyNoMoreInteractions(mRepositoryService);
    }

    @Test
    public void shouldLoadRemoteRepos() throws Exception {
        when(mRepositoryDao.getRepositoryCount()).thenReturn(0);

        mRepoRepositoryImpl.getLocalRepos(true, page, elementsPerPage);

        verify(mRepositoryDao, times(1)).getRepositoryCount();
        verify(mRepositoryService, times(1))
                .getRepositories(eq(page), eq(elementsPerPage));
        verifyNoMoreInteractions(mRepositoryDao);
        verifyNoMoreInteractions(mRepositoryService);
    }

    @Test
    public void properlyLoadLocalRepos() throws Exception {
        final List<Repository> fakeRepoList = (createFakeRepositoryList());
//
//        when(mRepositoryDao.getRepositoryCount()).thenReturn(21);
//        when(mRepositoryDao.getRepositories(page, elementsPerPage)).thenReturn(fakeRepoList);
//
//        mRepoRepositoryImpl.getLocalRepos(page, elementsPerPage, listener);
//
//        verify(listener, times(1)).onReposLoaded(fakeRepoList);
//        verifyNoMoreInteractions(listener);
    }

    @Test
    public void properlyLoadRemoteRepos() throws Exception {
        
    }

    @Test
    public void shouldNotifyLoadError() throws Exception {

    }

    private List<Repository> createFakeRepositoryList() {
        List<Repository> list = new ArrayList<>();
        Repository repository = new Repository();
        repository.setId("id-1");
        list.add(repository);
        return list;
    }

}


