package com.mph.xaccapp.domain.data.model;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Marcos on 17/12/2017.
 */

public interface RepositoryDao {

    Observable<List<Repository>> getRepositories(int page, int elementsPerPage);

    Repository getRepository(String id);

    void deleteAllRepositories();

    void deleteRepository(String id);

    void insertRepository(Repository repository);

    void insertRepositories(Iterable<Repository> repositories);

    int getRepositoryCount();
}
