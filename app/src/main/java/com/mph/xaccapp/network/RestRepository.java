package com.mph.xaccapp.network;

import com.google.gson.annotations.SerializedName;

public class RestRepository {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    public String description;

    public String ownerLogin;

    public RestRepository() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}
