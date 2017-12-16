package com.mph.xaccapp.main;

import java.util.List;

public interface MainView {

    void showRepositories(List<RepositoryViewModel> repositories);

    void addRepositories(List<RepositoryViewModel> repositories);

    void showProgress();

    void hideProgress();

    void showLoadError();

    void showDialogForRepository(RepositoryViewModel repository);

}
