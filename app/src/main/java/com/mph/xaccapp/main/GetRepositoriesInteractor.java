package com.mph.xaccapp.main;


import com.mph.xaccapp.model.Repository;

import java.util.List;

public interface GetRepositoriesInteractor {

    interface OnFinishedListener {

        void onRepositoriesLoaded(List<Repository> repositories);

        void onLoadError();

    }

    void execute(OnFinishedListener listener);
}
