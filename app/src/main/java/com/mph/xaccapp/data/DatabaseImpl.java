package com.mph.xaccapp.data;


import android.content.Context;
import android.support.annotation.NonNull;

import com.mph.xaccapp.BuildConfig;
import com.mph.xaccapp.model.Models;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

public class DatabaseImpl implements Database {


    @SuppressWarnings("unused")
    private static final String TAG = DatabaseImpl.class.getSimpleName();

    @NonNull
    private Context mContext;

    private EntityDataStore<Persistable> dataStore;

    public DatabaseImpl(@NonNull Context context) {
        mContext = context;
    }

    private EntityDataStore<Persistable> getData(Context context) {
        if (dataStore == null) {
            initDataSore(context);
        }
        return dataStore;
    }

    private void initDataSore(Context context) {
        DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, 8);
        if (BuildConfig.DEBUG) {
            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = source.getConfiguration();
        dataStore = new EntityDataStore<>(configuration);
    }

    @Override
    public EntityDataStore<Persistable> getDataStore() {
        return getData(mContext);
    }

    @Override
    public void clearDataStore() {
        initDataSore(mContext);
    }
}
