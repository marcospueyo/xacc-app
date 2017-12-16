package com.mph.xaccapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Marcos on 16/12/2017.
 */

public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<RestRepository>> getUserRepos(@Path("user") String userID);
}
