package com.halfplatepoha.jisho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by surjo on 21/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Japanese implements Serializable {
    private String word;
    private String reading;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }
}
