package com.mph.xaccapp.main;

import android.support.annotation.NonNull;

import com.mph.xaccapp.model.Repository;

import java.util.List;

public class GetRepositoriesInteractorImpl implements GetRepositoriesInteractor {

    @NonNull
    private final RepoRepository mRepoRepository;


    public GetRepositoriesInteractorImpl(@NonNull RepoRepository repoRepository) {
        mRepoRepository = repoRepository;
    }

    @Override
    public void execute(boolean forceRefresh, final int elementsPerPage, final int page,
                        final OnFinishedListener listener) {
        if (forceRefresh) {
            mRepoRepository.clearRepos(new RepoRepository.DeleteReposListener() {
                @Override
                public void onDeleteSuccess() {
                    getRepos(elementsPerPage, page, listener);
                }

                @Override
                public void onDeleteError() {

                }
            });
        }
        else {
            getRepos(elementsPerPage, page, listener);
        }
    }

    private void getRepos(int elementsPerPage, int page, final OnFinishedListener listener) {
        mRepoRepository.getRepos(page, elementsPerPage,
                new RepoRepository.GetReposListener() {
                    @Override
                    public void onReposLoaded(List<Repository> repositories) {
                        listener.onRepositoriesLoaded(repositories);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        listener.onLoadError();
                    }
                });
    }
}
