package com.mph.xaccapp.network;

import com.google.gson.annotations.SerializedName;

public class RestOwner {

    @SerializedName("id")
    public String id;

    @SerializedName("html_url")
    public String htmlURL;

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
}
