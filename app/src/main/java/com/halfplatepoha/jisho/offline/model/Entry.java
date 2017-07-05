package com.halfplatepoha.jisho.offline.model;

import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class Entry {
    private int entryId;
    private boolean isFavorite;
    private List<KanjiElement> kanjiElementElements;
    private List<ReadingElement> readingElementElements;
    private List<SenseElement> senseElementElements;

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<KanjiElement> getKanjiElementElements() {
        return kanjiElementElements;
    }

    public void setKanjiElementElements(List<KanjiElement> kanjiElementElements) {
        this.kanjiElementElements = kanjiElementElements;
    }

    public List<ReadingElement> getReadingElementElements() {
        return readingElementElements;
    }

    public void setReadingElementElements(List<ReadingElement> readingElementElements) {
        this.readingElementElements = readingElementElements;
    }

    public List<SenseElement> getSenseElementElements() {
        return senseElementElements;
    }

    public void setSenseElementElements(List<SenseElement> senseElementElements) {
        this.senseElementElements = senseElementElements;
    }
}
