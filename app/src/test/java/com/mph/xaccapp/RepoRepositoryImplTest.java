package com.mph.xaccapp;

import android.util.Log;

import com.mph.xaccapp.domain.data.RepoRepositoryImpl;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.data.model.RepositoryDao;
import com.mph.xaccapp.network.mapper.RestRepositoryMapper;
import com.mph.xaccapp.network.model.RestOwner;
import com.mph.xaccapp.network.model.RestRepository;
import com.mph.xaccapp.network.service.RepositoryService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


public final class RepoRepositoryImplTest {

    @SuppressWarnings("unused")
    private static final String TAG = RepoRepositoryImplTest.class.getSimpleName();

    private RepositoryDao mRepositoryDao;

    private RestRepositoryMapper mMapper;

    private RepoRepositoryImpl mRepoRepositoryImpl;

    private RepositoryService mRepositoryService;

    private Scheduler mBackgroundScheduler;

    private int page;

    private int elementsPerPage;

    private TestObserver<List<Repository>> testObserver;


    @Before
    public void setUp() throws Exception {
        mRepositoryDao = mock(RepositoryDao.class);
        mRepositoryService = mock(RepositoryService.class);
        mMapper = new RestRepositoryMapper();
        mBackgroundScheduler = new TestScheduler();
        page = 1;
        elementsPerPage = 10;
        testObserver = new TestObserver<>();

        mRepoRepositoryImpl = new RepoRepositoryImpl(mRepositoryService, mRepositoryDao, mMapper,
                mBackgroundScheduler);
    }

    @Test
    public void properlyLoadLocalRepos() throws Exception {
        final List<Repository> fakeRepoList = (createFakeRepositoryList(10));

        when(mRepositoryDao.getRepositories(page, elementsPerPage))
                .thenReturn(new Observable<List<Repository>>() {
                    @Override
                    protected void subscribeActual(Observer<? super List<Repository>> observer) {
                        observer.onNext(fakeRepoList);
                        observer.onComplete();
                    }
                });

        mRepoRepositoryImpl.getRepoPage(page, elementsPerPage).subscribe(testObserver);

        verify(mRepositoryDao, times(1))
                .getRepositories(eq(page), eq(elementsPerPage));

        testObserver.assertComplete();
        testObserver.assertValue(fakeRepoList);
    }

    @Test
    public void properlyFetchRemoteRepos() throws Exception {
        final int nElems = 25;
        final List<RestRepository> restRepositoryList = createFakeRemoteRepositoryList(nElems);
        final List<Repository> repositoryList = mMapper.map(restRepositoryList);

        when(mRepositoryService.getRepositories(page, elementsPerPage))
                .thenReturn(new Observable<List<RestRepository>>() {
            @Override
            protected void subscribeActual(Observer<? super List<RestRepository>> observer) {
                observer.onNext(restRepositoryList);
                observer.onComplete();
            }
        });

        when(mRepositoryDao.getRepositories(page, elementsPerPage)).thenReturn(
                new Observable<List<Repository>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Repository>> observer) {
                observer.onNext(repositoryList);
                observer.onComplete();
            }
        });

        mRepoRepositoryImpl.fetchRemoteRepos(page, elementsPerPage).subscribe(testObserver);

        verify(mRepositoryService, times(1))
                .getRepositories(page, elementsPerPage);
        verify(mRepositoryDao, times(1)).insertRepositories(repositoryList);

        testObserver.assertComplete();
    }

    @Test
    public void shouldNotifyLoadError() throws Exception {

    }

    private List<Repository> createFakeRepositoryList(int nElems) {
        List<Repository> list = new ArrayList<>();
        for (int i = 0; i < nElems; i++) {
            Repository repository = new Repository();
            repository.setId("id-" + String.valueOf(i));
            list.add(repository);
        }
        return list;
    }

    private List<RestRepository> createFakeRemoteRepositoryList(int nElems) {
        List<RestRepository> list = new ArrayList<>();
        for (int i = 0; i < nElems; i++) {
            RestRepository repository = new RestRepository();
            repository.setId("id-" + String.valueOf(i));
            RestOwner owner = new RestOwner();
            owner.setId("id-owner-" + String.valueOf(i));
            owner.setLogin("login-owner-" + String.valueOf(i));
            owner.setHtmlURL("html-owner-" + String.valueOf(i));
            repository.setOwner(owner);
            list.add(repository);
        }
        return list;
    }

}


