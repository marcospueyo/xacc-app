package com.mph.xaccapp.di.activity;

import com.mph.xaccapp.di.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                ActivityPresenterModule.class
        }
)
public interface ActivityComponent extends ActivityComponentInjects, ActivityComponentExposes  {


    final class Initializer {

        static public ActivityComponent init(final DaggerActivity daggerActivity,
                                             final ApplicationComponent applicationComponent) {
                        return DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule(daggerActivity))
                    .activityPresenterModule(new ActivityPresenterModule(daggerActivity))
                    .build();
        }

        private Initializer() {
        }
    }
}
