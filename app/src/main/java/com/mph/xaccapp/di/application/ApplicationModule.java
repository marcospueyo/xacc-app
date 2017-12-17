package com.mph.xaccapp.di.application;

import android.content.Context;
import android.content.res.Resources;

import com.mph.xaccapp.BuildConfig;
import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.di.ForApplication;
import com.mph.xaccapp.domain.data.model.Models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

@Module
public final class ApplicationModule {

    private final XACCApplication xaccApplication;

    public ApplicationModule(final XACCApplication xaccApplication) {
        this.xaccApplication = xaccApplication;
    }

    @Provides
    @Singleton
    XACCApplication provideXACCApplication() {
        return xaccApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return xaccApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return xaccApplication.getResources();
    }

    @Provides
    @Singleton
    EntityDataStore<Persistable> provideEntityDataStore() {
        DatabaseSource source = new DatabaseSource(xaccApplication, Models.DEFAULT, 9);
        if (BuildConfig.DEBUG) {
            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = source.getConfiguration();
        return  new EntityDataStore<>(configuration);
    }


    public interface Exposes {

        XACCApplication xaccApplication();

        @ForApplication
        Context context();

        Resources resources();

        EntityDataStore<Persistable> entityDataStore();
    }
}
