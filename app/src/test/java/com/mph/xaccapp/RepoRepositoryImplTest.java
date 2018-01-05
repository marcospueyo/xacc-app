package com.mph.xaccapp;

import android.util.Log;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.service.RepositoryService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


public final class RepoRepositoryImplTest {

    public static final String TAG = "RepoRepositoryImplTest";

    private RepositoryService mRepositoryService;

    private RepositoryDao mRepositoryDao;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl mRepoRepositoryImpl;

    @Before
    public void setUp() throws Exception {
        mRepositoryService = mock(RepositoryService.class);
        mRepositoryDao = mock(RepositoryDao.class);
        mMapper = new RestRepositoryMapper();
        mRepoRepositoryImpl = new RepoRepositoryImpl(mRepositoryService, mRepositoryDao, mMapper);
    }

    @Test
    public void shouldLoadLocalRepos() throws Exception {
        int page = 1;
        int elementsPerPage = 10;
        final List<Repository> fakeRepoList = (createFakeRepositoryList());
        final RepoRepository.GetReposListener listener = mock(RepoRepository.GetReposListener.class);

        when(mRepositoryDao.getRepositoryCount()).thenReturn(21);
        when(mRepositoryDao.getRepositories(page, elementsPerPage)).thenReturn(fakeRepoList);

        mRepoRepositoryImpl.getRepos(1, 10, listener);

        verify(mRepositoryDao, times(1)).getRepositoryCount();
        verify(mRepositoryDao, times(1)).getRepositories(page, elementsPerPage);
        verify(listener, times(1)).onReposLoaded(fakeRepoList);
        verifyNoMoreInteractions(listener);
        verifyNoMoreInteractions(mRepositoryService);

    }

    private List<Repository> createFakeRepositoryList() {
        List<Repository> list = new ArrayList<>();
        Repository repository = new Repository();
        repository.setId("id-1");
        list.add(repository);
        return list;
    }

}


