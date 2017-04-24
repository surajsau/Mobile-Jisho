package com.halfplatepoha.jisho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by surjo on 21/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Word implements Serializable {

    private boolean is_common;
    private ArrayList<String> tags;
    private ArrayList<Japanese> japanese;
    private ArrayList<Sense> senses;

    public boolean is_common() {
        return is_common;
    }

    public void setIs_common(boolean is_common) {
        this.is_common = is_common;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Japanese> getJapanese() {
        return japanese;
    }

    public void setJapanese(ArrayList<Japanese> japanese) {
        this.japanese = japanese;
    }

    public ArrayList<Sense> getSenses() {
        return senses;
    }

    public void setSenses(ArrayList<Sense> senses) {
        this.senses = senses;
    }
}
