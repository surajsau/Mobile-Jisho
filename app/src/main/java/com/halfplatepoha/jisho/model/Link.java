package com.halfplatepoha.jisho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by surjo on 21/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link implements Serializable {
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
