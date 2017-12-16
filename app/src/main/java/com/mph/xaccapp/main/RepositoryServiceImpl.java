package com.mph.xaccapp.main;

import android.support.annotation.NonNull;

import com.mph.xaccapp.network.GithubService;
import com.mph.xaccapp.network.RestRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryServiceImpl implements RepositoryService {

    public static final String TAG = "RepositoryServiceImpl";

    @NonNull
    private final GithubService mGithubService;

    @NonNull
    private final String mUserID;

    public RepositoryServiceImpl(@NonNull GithubService githubService, @NonNull String userID) {
        mGithubService = githubService;
        mUserID = userID;
    }

    @Override
    public void getRepositories(int page, int reposPerPage, final OnFetchCompletedListener listener) {
        int servicePage = page + 1; //page numbering is 1-based
        Call<List<RestRepository>> call = mGithubService.getUserRepos(mUserID,
                String.valueOf(servicePage), String.valueOf(reposPerPage));
        call.enqueue(new Callback<List<RestRepository>>() {
            @Override
            public void onResponse(Call<List<RestRepository>> call,
                                   Response<List<RestRepository>> response) {
                if (response.isSuccessful()) {
                    listener.onRepositoriesFetched(response.body());
                }
                else {
                    handleFetchFailed(listener);
                }
            }

            @Override
            public void onFailure(Call<List<RestRepository>> call, Throwable t) {
                handleFetchFailed(listener);
            }
        });
    }

    private void handleFetchFailed(OnFetchCompletedListener listener) {
        listener.onFetchFailed();
    }
}
