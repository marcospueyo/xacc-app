package com.mph.xaccapp.domain.data.model;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractRepository {

    @Key
    String id;

    String name;

    String description;

    String url;

    Boolean fork;

    String ownerLogin;

    String ownerUrl;
}
