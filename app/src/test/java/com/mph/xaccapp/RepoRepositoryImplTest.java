package com.mph.xaccapp;

import com.mph.xaccapp.domain.data.RepoRepository;
import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.service.RepositoryService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;


public final class RepoRepositoryImplTest {

    private RepositoryService mRepositoryService;

    private RepositoryDao mRepositoryDao;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl mRepoRepositoryImpl;

    @Before
    public void setUp() throws Exception {
        mRepositoryService = Mockito.mock(RepositoryService.class);
        mRepositoryDao = Mockito.mock(RepositoryDao.class);
        mMapper = new RestRepositoryMapper();
        mRepoRepositoryImpl = new RepoRepositoryImpl(mRepositoryService, mRepositoryDao, mMapper);
    }

    @Test
    public void shouldLoadLocalRepos() throws Exception {
        mRepoRepositoryImpl.getRepos(1, 10, new RepoRepository.GetReposListener() {
            @Override
            public void onReposLoaded(List<Repository> repositories) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}


