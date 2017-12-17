package com.mph.xaccapp.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.xaccapp.Router;
import com.mph.xaccapp.model.Repository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenterImpl implements MainPresenter {

    public static final String TAG = "MainPresenterImpl";

    @NonNull
    private final MainView mView;

    @NonNull
    private final GetRepositoriesInteractor mGetRepositoriesInteractor;

    @NonNull
    private final RepositoryViewModelMapper mMapper;

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
        mGetRepositoriesInteractor.execute(forceRefresh, mReposPerPage, mCurrentPage,
                new GetRepositoriesInteractor.OnFinishedListener() {
            @Override
            public void onRepositoriesLoaded(List<Repository> repositories) {
                Log.d(TAG, "onRepositoriesLoaded: " + repositories.size());
                mView.hideProgress();
                List<RepositoryViewModel> viewModels = mMapper.reverseMap(repositories);
                if (concatOperation) {
                    mView.addRepositories(viewModels);
                }
                else {
                    mView.showRepositories(viewModels);
                }
                mCurrentPage = prevPage + 1;
                mFetchInProcess = false;
            }

            @Override
            public void onLoadError() {
                mView.hideProgress();
                mView.showLoadError();
                mFetchInProcess = false;
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
