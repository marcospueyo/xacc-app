package com.mph.xaccapp.network;

import com.google.gson.annotations.SerializedName;

public class RestRepository {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("html_url")
    public String htmlURL;

    @SerializedName("owner")
    public RestOwner owner;


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

    public String getHtmlURL() {
        return htmlURL;
    }

    public void setHtmlURL(String htmlURL) {
        this.htmlURL = htmlURL;
    }

    public RestOwner getOwner() {
        return owner;
    }

    public void setOwner(RestOwner owner) {
        this.owner = owner;
    }
}
