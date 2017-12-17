package com.mph.xaccapp.di;


import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.di.activity.ActivityComponent;
import com.mph.xaccapp.di.activity.DaggerActivity;
import com.mph.xaccapp.di.application.ApplicationComponent;


public final class ComponentFactory {

    public ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(
            final XACCApplication xaccApplication) {
        return ApplicationComponent.Initializer.init(xaccApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity,
                                                            final XACCApplication xaccApplication) {
        return ActivityComponent.Initializer.init(daggerActivity,
                xaccApplication.getApplicationComponent());
    }
}
