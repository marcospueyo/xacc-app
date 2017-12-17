package com.mph.xaccapp.network.service;


import com.mph.xaccapp.network.model.RestRepository;

import java.util.List;

public interface RepositoryService {

    interface OnFetchCompletedListener {

        void onRepositoriesFetched(List<RestRepository> restRepositories);

        void onFetchFailed();

    }

    void getRepositories(int page, int reposPerPage, OnFetchCompletedListener listener);
}
