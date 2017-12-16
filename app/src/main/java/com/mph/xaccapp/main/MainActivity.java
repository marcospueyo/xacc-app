package com.mph.xaccapp.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mph.xaccapp.R;
import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.network.GithubService;
import com.mph.xaccapp.network.RestRepositoryMapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainView,
        SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter mPresenter;

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.listview) RecyclerView mListView;

    private RepositoryAdapter mAdapter;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPresenter();
        initListView();
        setForceRefreshListener();
    }

    private void initPresenter() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubService githubService = retrofit.create(GithubService.class);

        String userID = "xing";

        RepositoryService repositoryService = new RepositoryServiceImpl(githubService, userID);

        EntityDataStore<Persistable> dataStore =  ((XACCApplication) getApplication()).getData();

        RestRepositoryMapper mapper = new RestRepositoryMapper()
                ;
        RepoRepository repoRepository =
                new RepoRepositoryImpl(repositoryService, dataStore, mapper);

        GetRepositoriesInteractor getRepositoriesInteractor =
                new GetRepositoriesInteractorImpl(repoRepository);

        RepositoryViewModelMapper repositoryViewModelMapper = new RepositoryViewModelMapper();

        mPresenter = new MainPresenterImpl(this, getRepositoriesInteractor,
                repositoryViewModelMapper);
    }

    private void initListView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initializeRecyclerView(layoutManager);
        initializeAdapter(layoutManager);
    }

    private void initializeRecyclerView(LinearLayoutManager layoutManager) {
        layoutManager.setStackFromEnd(false);
        mListView.setLayoutManager(layoutManager);
        mListView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mListView.setHasFixedSize(true);
    }

    private void initializeAdapter(LinearLayoutManager layoutManager) {
        mAdapter = new RepositoryAdapter(this, mPresenter);
        mListView.setAdapter(mAdapter);
    }

    private void setForceRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.onForceRefresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void showRepositories(List<RepositoryViewModel> repositories) {
        Log.d(TAG, "showRepositories: " + repositories.size());
        mAdapter.updateItemList(repositories);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.repo_load_error),
                Snackbar.LENGTH_SHORT).show();
    }
}
