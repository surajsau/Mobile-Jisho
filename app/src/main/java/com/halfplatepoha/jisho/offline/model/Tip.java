package com.halfplatepoha.jisho.offline.model;

/**
 * Created by surjo on 05/07/17.
 */

public class Tip {
    private String title;
    private String body;

    public Tip(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
