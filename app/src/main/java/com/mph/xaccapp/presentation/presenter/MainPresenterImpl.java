package com.mph.xaccapp.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.presentation.navigation.Router;
import com.mph.xaccapp.domain.data.model.Repository;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.presentation.view.MainView;
import com.mph.xaccapp.presentation.model.RepositoryViewModel;
import com.mph.xaccapp.presentation.mapper.RepositoryViewModelMapper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainPresenterImpl implements MainPresenter {

    public static final String TAG = "MainPresenterImpl";

    @NonNull
    private final MainView mView;

    @NonNull
    private final GetRepositoriesInteractor mGetRepositoriesInteractor;

    @NonNull
    RepositoryViewModelMapper mMapper;

    @NonNull
    private final Router mRouter;

    private final int mReposPerPage;

    private int mCurrentPage;

    private boolean mFetchInProcess;

    public MainPresenterImpl(@NonNull MainView view,
                             @NonNull GetRepositoriesInteractor getRepositoriesInteractor,
                             @NonNull RepositoryViewModelMapper mapper, @NonNull Router router,
                             int reposPerPage) {
        mView = view;
        mGetRepositoriesInteractor = getRepositoriesInteractor;
        mMapper = mapper;
        mRouter = router;
        mReposPerPage = reposPerPage;

        mFetchInProcess = false;
    }


    @Override
    public void onStart() {
        mView.showProgress();
        cleanLoad(false);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onItemSelected(RepositoryViewModel repository) {
        mView.showDialogForRepository(repository);
    }

    @Override
    public void onScrollDown() {
        if (!mFetchInProcess) {
            mView.showProgress();
            loadRepositories(false, true);
        }
    }

    @Override
    public void onForceRefresh() {
        cleanLoad(true);
    }

    private void cleanLoad(boolean forceRefresh) {
        mCurrentPage = 0;
        loadRepositories(forceRefresh, false);
    }


    private void loadRepositories(final boolean forceRefresh, final boolean concatOperation) {
        mFetchInProcess = true;
        final int prevPage = mCurrentPage;

        mGetRepositoriesInteractor
                .execute(forceRefresh, mReposPerPage, mCurrentPage)
                .subscribe(new Observer<List<Repository>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(List<Repository> repositories) {
                mView.hideProgress();
                List<RepositoryViewModel> viewModels = mMapper.reverseMap(repositories);
                if (concatOperation) {
                    mView.addRepositories(viewModels);
                }
                else {
                    mView.showRepositories(viewModels);
                }
                if (repositories.size() > 0) {
                    mCurrentPage = prevPage + 1;
                }
                mFetchInProcess = false;
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showLoadError();
                mFetchInProcess = false;
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void onOpenRepoUrlSelected(RepositoryViewModel repository) {
        mRouter.openBrowser(repository.url());
    }

    @Override
    public void onOpenOwnerUrlSelected(RepositoryViewModel repository) {
        mRouter.openBrowser(repository.ownerUrl());
    }
}
