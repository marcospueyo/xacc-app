package com.mph.xaccapp.network.model;

import com.google.gson.annotations.SerializedName;

public class RestOwner {

    @SerializedName("id")
    private String id;

    @SerializedName("login")
    private String login;

    @SerializedName("html_url")
    private String htmlURL;

    public RestOwner() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlURL() {
        return htmlURL;
    }

    public void setHtmlURL(String htmlURL) {
        this.htmlURL = htmlURL;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
