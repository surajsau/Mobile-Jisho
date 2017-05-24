package com.halfplatepoha.jisho.offline.model;

import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class Reading {
    private int readingId;
    private String value;
    private boolean isTrueReading;
    private List<String> readingRelation;

    public int getReadingId() {
        return readingId;
    }

    public void setReadingId(int readingId) {
        this.readingId = readingId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTrueReading() {
        return isTrueReading;
    }

    public void setTrueReading(boolean trueReading) {
        isTrueReading = trueReading;
    }

    public List<String> getReadingRelation() {
        return readingRelation;
    }

    public void setReadingRelation(List<String> readingRelation) {
        this.readingRelation = readingRelation;
    }
}
