package com.halfplatepoha.jisho.db;

import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class FavLink extends RealmObject {
    private String text;
    private String url;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
