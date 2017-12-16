package com.mph.xaccapp.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.mph.xaccapp.R;
import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.network.GithubService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainView {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter mPresenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubService githubService = retrofit.create(GithubService.class);

        String userID = "xing";

        RepositoryService repositoryService = new RepositoryServiceImpl(githubService, userID);

        EntityDataStore<Persistable> dataStore =  ((XACCApplication) getApplication()).getData();

        RepoRepository repoRepository = new RepoRepositoryImpl(repositoryService, dataStore);

        GetRepositoriesInteractor getRepositoriesInteractor = new GetRepositoriesInteractorImpl(repoRepository);

        mPresenter = new MainPresenterImpl(this, getRepositoriesInteractor);
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
}
