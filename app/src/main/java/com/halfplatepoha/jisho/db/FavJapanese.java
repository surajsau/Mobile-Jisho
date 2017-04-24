package com.halfplatepoha.jisho.db;

import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class FavJapanese extends RealmObject {
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
