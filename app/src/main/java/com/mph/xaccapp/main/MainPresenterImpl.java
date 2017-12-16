package com.mph.xaccapp.main;

import android.support.annotation.NonNull;
import android.util.Log;

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

    public MainPresenterImpl(@NonNull MainView view,
                             @NonNull GetRepositoriesInteractor getRepositoriesInteractor,
                             @NonNull RepositoryViewModelMapper mapper) {
        mView = view;
        mGetRepositoriesInteractor = getRepositoriesInteractor;
        mMapper = mapper;
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
        Log.d(TAG, "onItemSelected: " + repository.id());
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
}
