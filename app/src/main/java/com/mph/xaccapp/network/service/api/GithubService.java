package com.mph.xaccapp.network.service.api;

import com.mph.xaccapp.network.model.RestRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Marcos on 16/12/2017.
 */

public interface GithubService {


    @GET("users/{user}/repos")
    Call<List<RestRepository>> getUserRepos(@Path("user") String userID, @Query("page") String page,
                                            @Query("per_page") String perPage);
}
