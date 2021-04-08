package com.mhalab.eveapi.model.action;

public abstract class RestCallAction implements Action {
    protected String url;

    public RestCallAction(String url) {
        this.url = url;
    }
}
