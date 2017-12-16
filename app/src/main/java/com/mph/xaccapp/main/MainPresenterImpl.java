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
        cleanRefresh();
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
            loadRepositories(true);
        }
    }

    @Override
    public void onForceRefresh() {
        cleanRefresh();
    }

    private void cleanRefresh() {
        mCurrentPage = 0;
        loadRepositories(false);
    }


    private void loadRepositories(final boolean concatOperation) {
        mFetchInProcess = true;
        mGetRepositoriesInteractor.execute(mReposPerPage, mCurrentPage,
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
                mCurrentPage++;
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
