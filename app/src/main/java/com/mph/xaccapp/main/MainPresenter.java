package com.mph.xaccapp.main;

public interface MainPresenter {

    void onStart();

    void onResume();

    void onDestroy();

    void onForceRefresh();

    void onItemSelected(RepositoryViewModel repository);

    void onOpenRepoUrlSelected(RepositoryViewModel repository);

    void onOpenOwnerUrlSelected(RepositoryViewModel repository);

}