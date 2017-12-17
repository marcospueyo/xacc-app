package com.mph.xaccapp.presentation.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mph.xaccapp.R;
import com.mph.xaccapp.di.activity.ActivityComponent;
import com.mph.xaccapp.di.activity.DaggerActivity;
import com.mph.xaccapp.presentation.model.RepositoryViewModel;
import com.mph.xaccapp.presentation.presenter.MainPresenter;
import com.mph.xaccapp.presentation.view.component.DividerItemDecoration;
import com.mph.xaccapp.presentation.view.MainView;
import com.mph.xaccapp.presentation.view.adapter.RepositoryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends DaggerActivity implements MainView,
        SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.listview) RecyclerView mListView;

    private RepositoryAdapter mAdapter;

    private static final int mElementsPerPage = 10;

    private static final int mScrollThreshold = 3;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();
        setForceRefreshListener();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private void initListView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initializeRecyclerView(layoutManager);
        initializeAdapter();
        initScrollListener(mElementsPerPage, mScrollThreshold, layoutManager);
    }

    private void initializeRecyclerView(final LinearLayoutManager layoutManager) {
        layoutManager.setStackFromEnd(false);
        mListView.setLayoutManager(layoutManager);
        mListView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mListView.setHasFixedSize(true);
    }

    private void initializeAdapter() {
        mAdapter = new RepositoryAdapter(this, mPresenter);
        mListView.setAdapter(mAdapter);
    }

    private void initScrollListener(final int elementsPerPage, final int scrollThreshold,
                                    final LinearLayoutManager layoutManager) {
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged: ");
                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                int repoCount = mAdapter.getItemCount();
                if (repoCount > (elementsPerPage - 1)
                        && Math.abs(repoCount - lastVisible) < scrollThreshold) {
                    Log.d(TAG, "onScrollStateChanged: should reload");
                    mPresenter.onScrollDown();
                }
            }
        });
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
        mAdapter.setItemList(repositories);
    }

    @Override
    public void addRepositories(List<RepositoryViewModel> repositories) {
        Log.d(TAG, "addRepositories: " + repositories.size());
        mAdapter.addToItemList(repositories);
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

    @Override
    public void showDialogForRepository(final RepositoryViewModel repository) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.open_repo_alert_text))
                .setItems(getResources().getStringArray(R.array.open_repo_options),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    mPresenter.onOpenRepoUrlSelected(repository);
                                }
                                else {
                                    mPresenter.onOpenOwnerUrlSelected(repository);
                                }

                            }
                        })
                .setCancelable(true)
                .show();
    }
}
