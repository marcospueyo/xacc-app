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

    public MainPresenterImpl(@NonNull MainView view,
                             @NonNull GetRepositoriesInteractor getRepositoriesInteractor,
                             @NonNull RepositoryViewModelMapper mapper, @NonNull Router router) {
        mView = view;
        mGetRepositoriesInteractor = getRepositoriesInteractor;
        mMapper = mapper;
        mRouter = router;
    }


    @Override
    public void onStart() {
        mView.showProgress();
        loadRepositories();
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
    public void onForceRefresh() {
        loadRepositories();
    }

    private void loadRepositories() {
        mGetRepositoriesInteractor.execute(new GetRepositoriesInteractor.OnFinishedListener() {
            @Override
            public void onRepositoriesLoaded(List<Repository> repositories) {
                Log.d(TAG, "onRepositoriesLoaded: " + repositories.size());
                mView.hideProgress();
                mView.showRepositories(mMapper.reverseMap(repositories));
            }

            @Override
            public void onLoadError() {
                mView.hideProgress();
                mView.showLoadError();
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
