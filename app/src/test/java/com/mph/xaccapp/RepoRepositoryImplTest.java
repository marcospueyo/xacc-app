package com.mph.xaccapp;

import android.util.Log;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.service.RepositoryService;
import com.mph.xaccapp.network.service.RepositoryServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


public final class RepoRepositoryImplTest {

    private RepositoryDao mRepositoryDao;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl mRepoRepositoryImpl;

    private RepositoryService mRepositoryService;

    private int page;

    private int elementsPerPage;

    private RepoRepository.GetReposListener listener;

    @Before
    public void setUp() throws Exception {
        mRepositoryDao = mock(RepositoryDao.class);
        mRepositoryService = mock(RepositoryService.class);
        listener = mock(RepoRepository.GetReposListener.class);
        mMapper = new RestRepositoryMapper();

        page = 1;
        elementsPerPage = 10;

        mRepoRepositoryImpl = new RepoRepositoryImpl(mRepositoryService, mRepositoryDao, mMapper);
    }

    @Test
    public void shouldLoadLocalRepos() throws Exception {
        when(mRepositoryDao.getRepositoryCount()).thenReturn(21);

        mRepoRepositoryImpl.getRepos(1, 10, listener);

        verify(mRepositoryDao, times(1)).getRepositoryCount();
        verify(mRepositoryDao, times(1))
                .getRepositories(page, elementsPerPage);
        verifyNoMoreInteractions(mRepositoryDao);
        verifyNoMoreInteractions(mRepositoryService);
    }

    @Test
    public void shouldLoadRemoteRepos() throws Exception {
        when(mRepositoryDao.getRepositoryCount()).thenReturn(0);

        mRepoRepositoryImpl.getRepos(page, elementsPerPage, listener);

        verify(mRepositoryDao, times(1)).getRepositoryCount();
        verify(mRepositoryService, times(1))
                .getRepositories(eq(page), eq(elementsPerPage),
                        Mockito.any(RepositoryService.OnFetchCompletedListener.class));
        verifyNoMoreInteractions(listener);
        verifyNoMoreInteractions(mRepositoryDao);
        verifyNoMoreInteractions(mRepositoryService);
    }

    @Test
    public void properlyLoadLocalRepos() throws Exception {
        final List<Repository> fakeRepoList = (createFakeRepositoryList());

        when(mRepositoryDao.getRepositoryCount()).thenReturn(21);
        when(mRepositoryDao.getRepositories(page, elementsPerPage)).thenReturn(fakeRepoList);

        mRepoRepositoryImpl.getRepos(page, elementsPerPage, listener);

        verify(listener, times(1)).onReposLoaded(fakeRepoList);
        verifyNoMoreInteractions(listener);
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


