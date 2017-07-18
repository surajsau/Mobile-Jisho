package com.halfplatepoha.jisho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.halfplatepoha.jisho.offline.model.Entry;
import com.halfplatepoha.jisho.offline.model.ListEntry;
import com.halfplatepoha.jisho.offline.model.SenseElement;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by surjo on 21/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Word implements Serializable {

    private int offlineEntryId;

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

    public int getOfflineEntryId() {
        return offlineEntryId;
    }

    public void setOfflineEntryId(int offlineEntryId) {
        this.offlineEntryId = offlineEntryId;
    }

    public static Word fromOfflineListEntry(ListEntry offlineListEntry) {
        Word word = new Word();
        Japanese jap = new Japanese();

        jap.setWord(offlineListEntry.getKanji());
        jap.setReading(offlineListEntry.getReading());

        Sense sense = new Sense();
        sense.setEnglish_definitions(new ArrayList<>(offlineListEntry.getGloss()));

        ArrayList<Japanese> japs = new ArrayList<>();
        ArrayList<Sense> senses = new ArrayList<>();

        japs.add(jap);
        senses.add(sense);

        word.setJapanese(japs);
        word.setSenses(senses);
        word.setOfflineEntryId(offlineListEntry.getEntryId());

        return word;
    }

    public static Word fromOfflineEntry(Entry offlineEntry) {
        Word word = new Word();
        Japanese jap = new Japanese();

        if(offlineEntry.getKanjiElementElements() != null && !offlineEntry.getKanjiElementElements().isEmpty())
            jap.setWord(offlineEntry.getKanjiElementElements().get(0).getValue());

        jap.setReading(offlineEntry.getReadingElementElements().get(0).getValue());

        ArrayList<Japanese> japs = new ArrayList<>();
        japs.add(jap);
        word.setJapanese(japs);

        if(offlineEntry != null) {
            ArrayList<Sense> senses = new ArrayList<>();
            for(SenseElement senseElement :  offlineEntry.getSenseElementElements()) {
                senses.add(Sense.fromOfflineSenseElement(senseElement));
            }
            word.setSenses(senses);
        }

        return word;
    }
}
