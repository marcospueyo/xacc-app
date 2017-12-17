package com.mph.xaccapp.di.activity;

import android.app.Activity;
import android.content.Context;

import com.mph.xaccapp.presentation.navigation.Router;
import com.mph.xaccapp.presentation.navigation.RouterImpl;
import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.presentation.presenter.MainPresenter;
import com.mph.xaccapp.presentation.presenter.MainPresenterImpl;
import com.mph.xaccapp.presentation.view.MainView;
import com.mph.xaccapp.presentation.mapper.RepositoryViewModelMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    public static final int ELEMENTS_PER_PAGE = 10;

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
    Router provideRouter() {
        return new RouterImpl(daggerActivity);
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(RepositoryViewModelMapper repositoryViewModelMapper,
                                       Router router,
                                       GetRepositoriesInteractor getRepositoriesInteractor) {
        final MainPresenter mainPresenter =
                new MainPresenterImpl((MainView) daggerActivity, getRepositoriesInteractor,
                repositoryViewModelMapper, router, ELEMENTS_PER_PAGE);
        daggerActivity.getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }

    public interface Exposes {

        Activity activity();

        @ForActivity
        Context context();

    }
}
