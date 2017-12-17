package com.mph.xaccapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.mph.xaccapp.data.Database;
import com.mph.xaccapp.data.DatabaseImpl;
import com.mph.xaccapp.di.application.ApplicationComponent;
import com.mph.xaccapp.di.ComponentFactory;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public class XACCApplication extends MultiDexApplication {

    private Context mContext;

    private Database mDatabase;

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

    public Context getContext() {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        return mContext;
    }

    public Database getDatabase() {
        if (mDatabase == null) {
            mDatabase = new DatabaseImpl(getContext());
        }
        return mDatabase;
    }

    public EntityDataStore<Persistable> getData() {
        return getDatabase().getDataStore();
    }

}
