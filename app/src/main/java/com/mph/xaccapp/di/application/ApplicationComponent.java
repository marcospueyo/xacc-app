package com.mph.xaccapp.di.application;


import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.di.application.module.MappersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        MappersModule.class
})
public interface ApplicationComponent extends ApplicationComponentInjects,
        ApplicationComponentExposes {

    final class Initializer {

        static public ApplicationComponent init(final XACCApplication xaccApplication) {
            return DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(xaccApplication))
                    .mappersModule(new MappersModule())
                    .build();
        }

        private Initializer() {

        }
    }
}
