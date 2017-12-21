package com.mph.xaccapp.di.activity;
/* Created by macmini on 21/12/2017. */

import com.mph.xaccapp.domain.interactor.GetRepositoriesInteractor;
import com.mph.xaccapp.presentation.mapper.RepositoryViewModelMapper;
import com.mph.xaccapp.presentation.navigation.Router;
import com.mph.xaccapp.presentation.presenter.MainPresenter;
import com.mph.xaccapp.presentation.presenter.MainPresenterImpl;
import com.mph.xaccapp.presentation.view.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public final class ActivityPresenterModule {

    public static final int ELEMENTS_PER_PAGE = 10;


    private final DaggerActivity mDaggerActivity;

    public ActivityPresenterModule(final DaggerActivity daggerActivity) {
        mDaggerActivity = daggerActivity;
    }

    private ActivityComponent getActivityComponent() {
        return mDaggerActivity.getActivityComponent();
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(/*RepositoryViewModelMapper repositoryViewModelMapper,*/
                                       /*Router router,*/
                                       /*GetRepositoriesInteractor getRepositoriesInteractor*/) {
        final MainPresenter mainPresenter =
                new MainPresenterImpl(
                        (MainView) mDaggerActivity,
                        /*getRepositoriesInteractor,*/
                        /*repositoryViewModelMapper,*/
                        /*router,*/
                        ELEMENTS_PER_PAGE);
        getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }
}
