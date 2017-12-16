package com.mph.xaccapp.main;


import com.mph.xaccapp.network.RestRepository;

import java.util.List;

public interface RepositoryService {

    interface OnFetchCompletedListener {

        void onRepositoriesFetched(List<RestRepository> restRepositories);

        void onFetchFailed();

    }

    void getRepositories(OnFetchCompletedListener listener);
}
