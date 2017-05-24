package com.halfplatepoha.jisho.offline.model;

import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class ListEntry {
    private int entryId;
    private String kanji;
    private String reading;
    private List<String> gloss;

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public List<String> getGloss() {
        return gloss;
    }

    public void setGloss(List<String> gloss) {
        this.gloss = gloss;
    }
}
