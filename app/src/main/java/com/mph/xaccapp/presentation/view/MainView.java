package com.mph.xaccapp.presentation.view;

import com.mph.xaccapp.presentation.model.RepositoryViewModel;

import java.util.List;

public interface MainView {

    void showRepositories(List<RepositoryViewModel> repositories);

    void addRepositories(List<RepositoryViewModel> repositories);

    void showProgress();

    void hideProgress();

    void showLoadError();

    void showDialogForRepository(RepositoryViewModel repository);

}
