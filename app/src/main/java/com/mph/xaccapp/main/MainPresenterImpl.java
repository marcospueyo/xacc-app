package com.mph.xaccapp.main;

import android.support.annotation.NonNull;

import com.mph.xaccapp.model.Repository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenterImpl implements MainPresenter {

    @NonNull
    private final MainView mView;

    @NonNull
    private final GetRepositoriesInteractor mGetRepositoriesInteractor;

    public MainPresenterImpl(@NonNull MainView view,
                             @NonNull GetRepositoriesInteractor getRepositoriesInteractor) {
        mView = view;
        mGetRepositoriesInteractor = getRepositoriesInteractor;
    }


    @Override
    public void onStart() {
        mGetRepositoriesInteractor.execute(new GetRepositoriesInteractor.OnFinishedListener() {
            @Override
            public void onRepositoriesLoaded(List<Repository> repositories) {

            }

            @Override
            public void onLoadError() {

            }
        });
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }
}
