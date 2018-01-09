package com.halfplatepoha.jisho.apimodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse implements Serializable {

    private Meta meta;
    private ArrayList<Word> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<Word> getData() {
        return data;
    }

    public void setData(ArrayList<Word> data) {
        this.data = data;
    }
}