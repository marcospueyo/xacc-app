package com.mph.xaccapp.domain.data;


import com.mph.xaccapp.domain.data.model.Repository;

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

    void getRepos(int page, int maxCount, GetReposListener listener);

    void clearRepos(DeleteReposListener listener);
}
