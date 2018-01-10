package com.mph.xaccapp.domain.data;


import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RepoRepository {

    interface GetReposListener {

        void onReposLoaded(List<Repository> repositories);

        void onDataNotAvailable();

    }

    interface DeleteReposListener {

        void onDeleteSuccess();

        void onDeleteError();
    }

    void getRepos(int page, int maxCount, GetReposListener listener);

    Observable<List<Repository>> getRepos(int page, int maxCount);

    void clearRepos(DeleteReposListener listener);

    Completable clearRepos(boolean forceRefresh);

}
