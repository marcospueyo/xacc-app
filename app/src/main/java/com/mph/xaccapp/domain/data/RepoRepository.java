package com.mph.xaccapp.domain.data;


import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RepoRepository {

    Observable<List<Repository>> getAll();

    Observable<List<Repository>> getRepoPage(int page, int maxCount);

    Completable fetchRemoteRepos(int page, int maxCount);

    Completable clearRepos();

    int localRepoCount();

}
