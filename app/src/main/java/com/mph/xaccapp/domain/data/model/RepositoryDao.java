package com.mph.xaccapp.domain.data.model;

import java.util.List;

/**
 * Created by Marcos on 17/12/2017.
 */

public interface RepositoryDao {

    List<Repository> getRepositories(int page, int elementsPerPage);

    Repository getRepository(String id);

    void deleteAllRepositories();

    void deleteRepository(String id);

    void insertRepository(Repository repository);

    int getRepositoryCount();
}
