package com.mph.xaccapp.domain.interactor;


import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

public interface GetRepositoriesInteractor {

    interface OnFinishedListener {

        void onRepositoriesLoaded(List<Repository> repositories);

        void onLoadError();

    }

    void execute(boolean forceRefresh, int elementsPerPage, int page, OnFinishedListener listener);
}
