package com.mph.xaccapp.domain.data;


import com.mph.xaccapp.domain.data.model.Repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RepoRepository {

    Observable<List<Repository>> getRepos(final boolean mustFetchFromRemote, int page, int maxCount);

    Completable clearRepos(boolean forceRefresh);

}
