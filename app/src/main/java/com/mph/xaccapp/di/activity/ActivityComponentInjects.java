package com.mph.xaccapp.di.activity;

import com.mph.xaccapp.presentation.view.activity.MainActivity;
import com.mph.xaccapp.presentation.presenter.MainPresenter;


public interface ActivityComponentInjects {

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}
