package com.mph.xaccapp;

import android.support.multidex.MultiDexApplication;

import com.mph.xaccapp.di.application.ApplicationComponent;
import com.mph.xaccapp.di.ComponentFactory;

public class XACCApplication extends MultiDexApplication {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = ComponentFactory.createApplicationComponent(this);
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
