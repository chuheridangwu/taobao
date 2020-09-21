package com.cool.taobaojava.model.domain;

public class TicketParams {
    public String url;
    public String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TicketParams(String url, String title) {
        this.url = url;
        this.title = title;
    }
}
