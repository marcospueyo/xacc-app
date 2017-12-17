package com.mph.xaccapp.di.activity;

import com.mph.xaccapp.main.MainActivity;
import com.mph.xaccapp.main.MainPresenter;


public interface ActivityComponentInjects {

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}
