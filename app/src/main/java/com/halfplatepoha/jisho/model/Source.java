package com.halfplatepoha.jisho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by surjo on 21/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Source implements Serializable {

    private String language;
    private String word;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
