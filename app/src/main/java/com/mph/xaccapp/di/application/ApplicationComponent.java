package com.mph.xaccapp.di.application;


import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.di.application.module.DataModule;
import com.mph.xaccapp.di.application.module.InteractorModule;
import com.mph.xaccapp.di.application.module.MappersModule;
import com.mph.xaccapp.di.application.module.NetworkModule;
import com.mph.xaccapp.di.application.module.RepositoryModule;
import com.mph.xaccapp.di.application.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        MappersModule.class,
        NetworkModule.class,
        ServiceModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        DataModule.class
})
public interface ApplicationComponent extends ApplicationComponentInjects,
        ApplicationComponentExposes {

    final class Initializer {

        static public ApplicationComponent init(final XACCApplication xaccApplication) {
            return DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(xaccApplication))
                    .mappersModule(new MappersModule())
                    .networkModule(new NetworkModule())
                    .serviceModule(new ServiceModule())
                    .repositoryModule(new RepositoryModule())
                    .interactorModule(new InteractorModule())
                    .dataModule(new DataModule())
                    .build();
        }

        private Initializer() {

        }
    }
}
