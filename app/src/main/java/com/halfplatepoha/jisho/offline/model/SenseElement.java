package com.halfplatepoha.jisho.offline.model;

import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class SenseElement {
    private int senseId;
    private List<String> partsOfSpeech;
    private List<String> fieldOfApplication;
    private List<String> dialect;
    private List<String> glosses;

    public int getSenseId() {
        return senseId;
    }

    public void setSenseId(int senseId) {
        this.senseId = senseId;
    }

    public List<String> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(List<String> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public List<String> getFieldOfApplication() {
        return fieldOfApplication;
    }

    public void setFieldOfApplication(List<String> fieldOfApplication) {
        this.fieldOfApplication = fieldOfApplication;
    }

    public List<String> getDialect() {
        return dialect;
    }

    public void setDialect(List<String> dialect) {
        this.dialect = dialect;
    }

    public List<String> getGlosses() {
        return glosses;
    }

    public void setGlosses(List<String> glosses) {
        this.glosses = glosses;
    }
}
