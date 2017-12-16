package com.mph.xaccapp.model;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractRepository {

    @Key
    String id;

    String name;

    String description;

    String ownerLogin;
}
