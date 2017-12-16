package com.mph.xaccapp.main;

import com.mph.xaccapp.model.Repository;
import com.mph.xaccapp.utils.Mapper;

public class RepositoryViewModelMapper extends Mapper<RepositoryViewModel, Repository> {

    @Override
    public Repository map(RepositoryViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RepositoryViewModel reverseMap(Repository value) {
        return RepositoryViewModel.builder()
                .setId(value.getId())
                .setTitle(value.getName())
                .setDescription(value.getDescription())
                .setLogin(value.getOwnerLogin())
                .setUrl(value.getUrl())
                .setOwnerUrl(value.getOwnerUrl())
                .setFork(value.isFork())
                .build();
    }
}
