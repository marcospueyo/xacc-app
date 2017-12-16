package com.mph.xaccapp.data;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public interface Database {

    EntityDataStore<Persistable> getDataStore();

    void clearDataStore();
}
