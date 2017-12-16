package com.mph.xaccapp.network;

import com.mph.xaccapp.model.Repository;
import com.mph.xaccapp.utils.Mapper;

public class RestRepositoryMapper extends Mapper<RestRepository, Repository> {

    @Override
    public Repository map(RestRepository value) {
        Repository repository = new Repository();
        repository.setId(value.getId());
        repository.setName(value.getName());
        repository.setDescription(value.getDescription());
        repository.setUrl(value.getHtmlURL());
        repository.setFork(value.getFork());
        repository.setOwnerLogin(value.getOwner().getLogin());
        repository.setOwnerUrl(value.getOwner().getHtmlURL());
        return repository;
    }

    @Override
    public RestRepository reverseMap(Repository value) {
        throw new UnsupportedOperationException();
    }
}
