package com.mph.xaccapp.main;


import com.mph.xaccapp.model.Repository;

import java.util.List;

public interface RepoRepository {

    interface GetReposListener {

        void onReposLoaded(List<Repository> repositories);

        void onDataNotAvailable();

    }

    void getRepos(GetReposListener listener);
}
