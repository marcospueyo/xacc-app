package com.mph.xaccapp.presentation.presenter;

import com.mph.xaccapp.presentation.model.RepositoryViewModel;

public interface MainPresenter {

    void onStart();

    void onResume();

    void onDestroy();

    void onForceRefresh();

    void onScrollDown();

    void onItemSelected(RepositoryViewModel repository);

    void onOpenRepoUrlSelected(RepositoryViewModel repository);

    void onOpenOwnerUrlSelected(RepositoryViewModel repository);

}