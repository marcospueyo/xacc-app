package com.mph.xaccapp.data;


import com.mph.xaccapp.data.model.Repository;

import java.util.List;

public interface RepoRepository {

    interface GetReposListener {

        void onReposLoaded(List<Repository> repositories);

        void onDataNotAvailable();

    }

    interface DeleteReposListener {

        void onDeleteSuccess();

        void onDeleteError();
    }

    void getRepos(GetReposListener listener);

    void getRepos(int maxCount, GetReposListener listener);

    void getRepos(int page, int maxCount, GetReposListener listener);

    void clearRepos(DeleteReposListener listener);
}
