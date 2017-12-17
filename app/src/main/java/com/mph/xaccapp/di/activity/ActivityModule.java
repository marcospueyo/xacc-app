package com.mph.xaccapp.di.activity;

import android.app.Activity;
import android.content.Context;

import com.mph.xaccapp.Router;
import com.mph.xaccapp.RouterImpl;
import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.main.GetRepositoriesInteractor;
import com.mph.xaccapp.main.GetRepositoriesInteractorImpl;
import com.mph.xaccapp.main.MainPresenter;
import com.mph.xaccapp.main.MainPresenterImpl;
import com.mph.xaccapp.main.MainView;
import com.mph.xaccapp.main.RepoRepository;
import com.mph.xaccapp.main.RepoRepositoryImpl;
import com.mph.xaccapp.main.RepositoryService;
import com.mph.xaccapp.main.RepositoryServiceImpl;
import com.mph.xaccapp.main.RepositoryViewModelMapper;
import com.mph.xaccapp.network.GithubService;
import com.mph.xaccapp.network.RestRepositoryMapper;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ActivityModule {

    private final DaggerActivity daggerActivity;

    public ActivityModule(final DaggerActivity daggerActivity) {
        this.daggerActivity = daggerActivity;
    }

    @Provides
    @ActivityScope
    @ForActivity
    Context provideActivityContext() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubService githubService = retrofit.create(GithubService.class);

        String userID = "xing";
        int mElementsPerPage = 10;
        int mScrollThreshold = 3;

        RepositoryService repositoryService = new RepositoryServiceImpl(githubService, userID);


        EntityDataStore<Persistable> dataStore =  ((XACCApplication) daggerActivity.getApplication()).getData();

        RestRepositoryMapper mapper = new RestRepositoryMapper();
        RepoRepository repoRepository =
                new RepoRepositoryImpl(repositoryService, dataStore, mapper);

        GetRepositoriesInteractor getRepositoriesInteractor =
                new GetRepositoriesInteractorImpl(repoRepository);

        Router router = new RouterImpl(daggerActivity);

        RepositoryViewModelMapper repositoryViewModelMapper = new RepositoryViewModelMapper();



        final MainPresenter mainPresenter = new MainPresenterImpl((MainView) daggerActivity, getRepositoriesInteractor,
                repositoryViewModelMapper, router, mElementsPerPage);
        daggerActivity.getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }

    public interface Exposes {

        Activity activity();

        @ForActivity
        Context context();
    }
}
