package com.mph.xaccapp.network.service;


import com.mph.xaccapp.network.model.RestRepository;

import java.util.List;

import io.reactivex.Observable;

public interface RepositoryService {

    Observable<List<RestRepository>> getRepositories(int page, int reposPerPage);
}
