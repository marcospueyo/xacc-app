package com.mph.xaccapp;

import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.service.RepositoryService;

import org.junit.Before;
import org.mockito.Mockito;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public final class RepoRepositoryImplTest {

    private RepositoryService mRepositoryService;

    private EntityDataStore<Persistable> mDataStore;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl repoRepositoryImpl;

    @Before
    public void setUp() throws Exception {
        mRepositoryService = Mockito.mock(RepositoryService.class);
//        mDataStore = Mockito.mock()
    }

}


