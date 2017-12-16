package com.mph.xaccapp.main;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenterImpl implements MainPresenter {

    @NonNull
    private final MainView mView;

    public MainPresenterImpl(@NonNull MainView mainView) {
        mView = checkNotNull(mainView);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }
}
