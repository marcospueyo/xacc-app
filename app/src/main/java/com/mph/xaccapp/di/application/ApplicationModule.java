package com.mph.xaccapp.di.application;

import android.content.Context;
import android.content.res.Resources;

import com.mph.xaccapp.XACCApplication;
import com.mph.xaccapp.di.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

    public interface Exposes {

        XACCApplication xaccApplication();

        @ForApplication
        Context context();

        Resources resources();
    }
}
